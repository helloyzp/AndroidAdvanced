package com.example.javaadvanced.performanceOptimization.Bitmap.bitmapcache;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaadvanced.R;

/**
 * Bitma内存缓存、磁盘缓存、Bitmap对象复用(避免Bitmap频繁的创建与销毁(内存抖动)，从而避免造成卡顿与OOM，提高程序性能)
 *
 * 当内存缓存中没有读取到时，并且复用池中有Bitmap对象符合复用条件时可以复用这个Bitmap对象，
 * 这样去磁盘中读取缓存时就可以避免创建新的Bitmap对象，从log中也可以看到磁盘中读取的Bitmap对象
 * 和复用池中获取的Bitmap对象是同一个。
 *
 * BitmapAdapter: 使用内存缓存null
 * BitmapAdapter: 使用复用缓存android.graphics.Bitmap@69df41f
 * BitmapAdapter: 使用磁盘缓存android.graphics.Bitmap@69df41f
 * BitmapAdapter: 使用内存缓存null
 * BitmapAdapter: 使用复用缓存android.graphics.Bitmap@71b85ca
 * BitmapAdapter: 使用磁盘缓存android.graphics.Bitmap@71b85ca
 * BitmapAdapter: 使用内存缓存null
 * BitmapAdapter: 使用复用缓存android.graphics.Bitmap@75c17b1
 * BitmapAdapter: 使用磁盘缓存android.graphics.Bitmap@75c17b1
 */
public class BitmapAdapter extends RecyclerView.Adapter<BitmapAdapter.BitmapViewHolder> {
    private String TAG = "BitmapAdapter";

    private Context context;

    public BitmapAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BitmapViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_bitmap, null, false);
        BitmapViewHolder bitmapViewHolder = new BitmapViewHolder(view);
        return bitmapViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BitmapViewHolder bitmapViewHolder, int i) {

        // 原始方法获取bitmap，获取原图，不进行压缩
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_mv_webp);

        // 第一种优化：进行图片压缩
//        Bitmap bitmap = ImageResize.resizeBitmap(context, R.drawable.icon_mv_jpg, 80, 80, false);

        // 第二种优化：使用缓存，并使用Bitmap复用机制，避免频繁的创建与销毁Bitmap对象。
        Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(String.valueOf(i));
        Log.e(TAG, "使用内存缓存" + bitmap);
        if (bitmap == null) {
            //当内存缓存中没有读取到Bitmap时需要去磁盘缓存中读取，这时不是直接创建一个新的Bitmap，而是去复用池中
            //检索有没有可以复用的Bitmap，传递给options.inBitmap使用，这样BitmapFactory.decodeXXX()系列方法进行创建Bitmap
            //时就会复用这个从复用池中检索出来的Bitmap，避免Bitmap频繁的创建与销毁。
            Bitmap reusable = ImageCache.getInstance().getReusable(60, 60, 1);
            Log.e(TAG, "使用复用缓存" + reusable);

            bitmap = ImageCache.getInstance().getBitmapFromDisk(String.valueOf(i), reusable);
            Log.e(TAG, "使用磁盘缓存" + reusable);

            if (bitmap == null) {
                // 网络获取
                bitmap = ImageResize.resizeBitmap(context, R.drawable.icon_mv_jpg, 80, 80, false, reusable);
                //放入内存
                ImageCache.getInstance().putBitmap2Memory(String.valueOf(i), bitmap);
                //放入磁盘
                ImageCache.getInstance().putBitmap2Disk(String.valueOf(i), bitmap);
            }

        }

        bitmapViewHolder.iv.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return 1000;
    }

    class BitmapViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public BitmapViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }

    }
}
