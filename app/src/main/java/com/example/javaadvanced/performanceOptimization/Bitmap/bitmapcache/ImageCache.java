package com.example.javaadvanced.performanceOptimization.Bitmap.bitmapcache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.LruCache;


import com.example.javaadvanced.performanceOptimization.Bitmap.bitmapcache.disk.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ImageCache {

    private int VERSION_CODE = 1;

    private static ImageCache instance;

    /**
     * 单例类
     *
     * @return
     */
    public static ImageCache getInstance() {
        if (instance == null) {
            synchronized (ImageCache.class) {
                if (instance == null) {
                    instance = new ImageCache();
                }
            }
        }
        return instance;
    }

    private LruCache<String, Bitmap> lruCache; //内存缓存
    private Set<WeakReference<Bitmap>> reusablePool; // 复用池，避免频繁的创建与销毁Bitmap对象，结合options.inBitmap使用。
    private DiskLruCache diskLruCache; //磁盘缓存


    public void init(Context context, String dir) {

        // 复用池
        reusablePool = Collections.synchronizedSet(new HashSet<WeakReference<Bitmap>>());

        // 内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();

        //memoryClass * 1024 * 1024 / 8
        lruCache = new LruCache<String, Bitmap>(memoryClass * 1024 * 1024 / 8) {
            // 返回的一张图片大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    return value.getAllocationByteCount();
                }
                return value.getByteCount();
            }

            //LruCache移除图片的时候回调 entryRemoved 方法
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                //当Bitmap移出lruCache时，判断oldValue.isMutable()是否为true，如果是可变的，则表示可以复用，
                if (oldValue.isMutable()) {//如果可以复用，将Bitmap放入复用池中，目的是为了复用Bitmap这块内存(避免Bitmap频繁的创建与销毁)。
                    // 3.0 bitmap 像素数据存储在 native
                    // <8.0  bitmap 像素数据存储在 java
                    // 8.0以后 像素数据存储在 native
                    //放入复用池中的Bitmap不能调用 recycle()，否则内存就被回收了
                    reusablePool.add(new WeakReference<Bitmap>(oldValue, getReferenceQueue()));
                } else { //如果不能复用，我们就直接调用 recycle() 回收内存
                    oldValue.recycle();//调用 recycle() 回收内存
                }
            }
        };
        try {
            diskLruCache = DiskLruCache.open(new File(dir), VERSION_CODE, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
            //指定的目录创建DiskLruCache失败时用系统的缓存目录
            try {
                diskLruCache = DiskLruCache.open(context.getCacheDir(), VERSION_CODE, 1, 10 * 1024 * 1024);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 放入磁盘缓存
     */
    public void putBitmap2Disk(String key, Bitmap bitmap) {
        DiskLruCache.Snapshot snapshot = null;
        OutputStream os = null;
        try {
            snapshot = diskLruCache.get(key);
            if (snapshot == null) {
                DiskLruCache.Editor edit = diskLruCache.edit(key);
                if (edit != null) {
                    os = edit.newOutputStream(0);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                    edit.commit();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从磁盘缓存获取bitmap
     *
     * @param key
     * @param reusable
     * @return
     */
    public Bitmap getBitmapFromDisk(String key, Bitmap reusable) {
        DiskLruCache.Snapshot snapshot = null;
        Bitmap bitmap = null;
        try {
            snapshot = diskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            InputStream is = snapshot.getInputStream(0);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inBitmap = reusable;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            if (bitmap != null) {
                lruCache.put(key, bitmap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return bitmap;
    }

    private ReferenceQueue<Bitmap> referenceQueue;
    boolean shutDown;

    /**
     * 为什么使用引用队列？
     *
     * 因为gc回收Bitmap时只是回收了Bitmap在堆中的这部分内存，Bitmap的像素数据是存储在native内存中，
     * 所以引用队列监控到Bitmap被gc回收时需要手动调用bitmap.recycle()回收native中的像素数据占用的内存。
     *
     * @return
     */
    private ReferenceQueue<Bitmap> getReferenceQueue() {
        if (referenceQueue == null) {
            referenceQueue = new ReferenceQueue<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!shutDown) {
                        try {
                            Reference<? extends Bitmap> remove = referenceQueue.remove();
                            Bitmap bitmap = remove.get();
                            if (bitmap != null && !bitmap.isRecycled()) {
                                bitmap.recycle();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        return referenceQueue;
    }

    /**
     * 把bitmap 放入内存
     *
     * @param key
     * @param bitmap
     */
    public void putBitmap2Memory(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }

    /**
     * 从内存中取出bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemory(String key) {
        return lruCache.get(key);
    }

    public void clearMemory() {
        lruCache.evictAll();
    }


    /**
     * Bitmap复用规则：
     * 3.0 之前不能复用
     * 3.0-4.4 宽高一样，inSampleSize = 1
     * 4.4 只要小于等于就行了
     *
     * @param w
     * @param h
     * @param inSampleSize
     * @return
     */
    public Bitmap getReusable(int w, int h, int inSampleSize) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }
        Bitmap reusable = null;

        Iterator<WeakReference<Bitmap>> iterator = reusablePool.iterator();
        while (iterator.hasNext()) {
            Bitmap bitmap = iterator.next().get();
            if (bitmap != null) {
                if (checkInBitmap(bitmap, w, h, inSampleSize)) {
                    reusable = bitmap;
                    iterator.remove();
                    break;
                }
            } else {
                iterator.remove();
            }
        }

        return reusable;

    }

    /**
     * 校验bitmap 是否满足条件
     *
     * @param bitmap
     * @param w
     * @param h
     * @param inSampleSize
     * @return
     */
    private boolean checkInBitmap(Bitmap bitmap, int w, int h, int inSampleSize) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return bitmap.getWidth() == w && bitmap.getHeight() == h && inSampleSize == 1;
        }

        if (inSampleSize > 1) {
            w /= inSampleSize;
            h /= inSampleSize;
        }
        int byteCount = w * h * getBytesPerPixel(bitmap.getConfig());
        // 图片内存 <= 系统分配内存 则满足条件，即只要图片的内存<=Bitmap分配的内存就可以复用
        return byteCount <= bitmap.getAllocationByteCount();


    }

    /**
     * 通过像素格式计算每一个像素占用多少字节
     *
     * @param config
     * @return
     */
    private int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        }
        return 2;
    }

}
