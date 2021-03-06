package com.example.customglide;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.customglide.cache.ActiveCache;
import com.example.customglide.cache.MemoryCache;
import com.example.customglide.cache.MemoryCacheCallback;
import com.example.customglide.cache.disk.DiskLruCacheImpl;
import com.example.customglide.fragment.LifecycleCallback;
import com.example.customglide.load_data.LoadDataManager;
import com.example.customglide.load_data.ResponseListener;
import com.example.customglide.resource.Key;
import com.example.customglide.resource.Value;
import com.example.customglide.resource.ValueCallback;

/**
 * 图片资源加载引擎：即加载图片资源的引擎类
 */
public class RequestTargetEngine implements LifecycleCallback, ValueCallback, MemoryCacheCallback, ResponseListener {

    private final String TAG = RequestTargetEngine.class.getSimpleName();

    /**
     * 当Activity的onStart()方法执行时会触发该方法
     */
    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction(): Glide生命周期之 初始化...");
    }

    /**
     * 当Activity的onStop()方法执行时会触发该方法
     */
    @Override
    public void glideStopAction() {
        Log.d(TAG, "glideStopAction(): Glide生命周期之 停止中...");
    }

    /**
     * 当Activity的onDestroy()方法执行时会触发该方法
     */
    @Override
    public void glideRecycleAction() {
        Log.d(TAG, "glideRecycleAction(): Glide生命周期之 进行释放操作，缓存策略释放操作等...");

        if (activeCache != null) {
            activeCache.coloseThread(); // 将活动缓存给释放掉
        }
    }

    private ActiveCache activeCache; // 活动缓存
    private MemoryCache memoryCache; // 内存缓存
    private DiskLruCacheImpl diskLruCache; // 磁盘缓存

    // Glide使用的内存空间大小是总内存大小的八分之一
    // (为什么是八分之一，因为早期的Android系统默认有8个进程，八分之一即尽量获得最大的内存空间)
    // 当然我们这里简单使用60M内存缓存空间即可
    private final int MEMORY_MAX_SIXE = 1024 * 1024 * 60;

    /**
     * 构造函数
     */
    public RequestTargetEngine() {
        if (activeCache == null) {
            activeCache = new ActiveCache(this); // 回调给外界，Value资源不再使用了 设置监听
        }
        if (memoryCache == null) {
            memoryCache = new MemoryCache(MEMORY_MAX_SIXE); // 内存缓存
            memoryCache.setMemoryCacheCallback(this);
        }

        // 初始化磁盘缓存
        diskLruCache = new DiskLruCacheImpl();
    }

    public void into(ImageView imageView) {
        this.imageView = imageView;

        Tool.checkNotEmpty(imageView);
        Tool.assertMainThread(); // 非主线程 抛出异常

        // 触发 缓存机制
        // TODO 加载资源 ---》缓存机制 ---》网络/sd/加载资源成功后 ---》把资源保存到缓存中
        Value value = cacheAction();
        if (null != value) {
            // null != value  ==  一定使用了一次 + 1

            // 使用完成了 - 1
            value.nonUseAction();

            imageView.setImageBitmap(value.getmBitmap()); // 显示图片
        }
    }

    /**
     * TODO 加载资源 ---》缓存机制 ---》网络/sd/加载资源成功后 ---》把资源保存到缓存中
     */
    private Value cacheAction() {
        // TODO 第一步：判断活动缓存是否有资源，如果有资源，就返回，  否则就继续往下找
        Value value = activeCache.get(key);
        if(null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（活动缓存）中获取的资源>>>");

            // 返回就代表了使用了一次Value，所以+1
            value.useAction(); // +1

            return value; //活动缓存中有资源，则直接返回
        }

        // TODO 第二步：判断内存缓存是否对资源，如果有资源，则进行移动资源（内存缓存--->活动缓存），然后返回，否则就继续往下找
        value = memoryCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（内存缓存）中获取的资源>>>");

            Log.d(TAG, "cacheAction: 移动到活动缓存");
            // 移动操作 （内存缓存--->活动缓存）
            memoryCache.shoudonRemove(key); // 从内存缓存中移除
            activeCache.put(key, value); // 加入到活动缓存中

            // 返回就代表了使用了一次Value，所以+1
            value.useAction(); // +1

            return value;
        }

        // TODO 第三步：从磁盘缓存中去找，如果找到了，则把磁盘缓存中的元素加入到活动缓存中，
        //  注意这时不是移动元素，磁盘缓存中还是保留这个元素
        value = diskLruCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（磁盘缓存）中获取的资源>>>");

            Log.d(TAG, "cacheAction: 移动到活动缓存");
            // 把磁盘缓存中的元素加入活动缓存中(注意这里是复制，不是移动)
            activeCache.put(key, value);

            // ... 可能会有后续扩展 ...

            // 返回就代表了使用了一次Value，所以+1
            value.useAction(); // +1

            return value;
        }

        // TODO 第四步， 真正去加载外部资源(即缓存之外的资源)，网络、SDcard 等等
        value = new LoadDataManager().loadResource(path, this, glideContext);
        if(value != null) {
            return value;
        }

        return null; // 有意这样做的，为了后需好判断
    }

    private String path;
    private Context glideContext;
    private String key; // ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668 (磁盘缓存用的key)
    private ImageView imageView; // 显示的目标

    /**
     * RequestManager传递过来的值
     * @param path
     * @param requestManagerContext
     */
    public void loadValueInitAction(String path, Context requestManagerContext) {
        this.path = path;
        this.glideContext = requestManagerContext;
        this.key = new Key(path).getKey();
    }

    /**
     * 活动缓存间接的调用，是 Value发出的
     * 专门给Value，不再使用的回调接口
     * 监听的方法（Value不再使用了）
     * @param key
     * @param value
     */
    @Override
    public void valueNonUseListener(String key, Value value) { // valueNonUseListener()方法被调用说明图片已经从活动缓存中移除
        Log.i(TAG, "valueNonUseListener(),key=" + key);


        Log.i(TAG, "valueNonUseListener(),key=" + key + " ,加入到内存缓存");
        //  加入到内存缓存
        if (key != null && value != null) {
            memoryCache.put(key, value);
        }
    }

    /**
     * 活动缓存 的 LRU 被动移除后，回调的函数
     * 内存缓存中，LRU移除 回调的接口
     * 内存缓存中移除的 key--value
     * @param key
     * @param oldValue
     */
    @Override
    public void entryRemovedMemoryCache(String key, Value oldValue) {

    }

    /**
     * 外置资源(如网络资源等)请求成功的回调
     * @param value
     */
    @Override
    public void responseSuccess(Value value) {
        if (null != value) {
            saveCahce(key, value); // 调用保存缓存的方法
            imageView.setImageBitmap(value.getmBitmap()); // 显示给目标视图控件
        }
    }

    /**
     * 外置资源请求失败的回调
     * @param e
     */
    @Override
    public void responseException(Exception e) {
        Log.d(TAG, "responseException: 加载外部资源失败 e:" + e.getMessage());
    }

    /**
     * 外置资源加载成功后，保存到磁盘缓存
     */
    private void saveCahce(String key, Value value) {
        Log.d(TAG, "saveCahce(): 加载外置资源成功后 ，保存到缓存中， key:" + key + " value:" + value);

        value.setKey(key);

        //为什么不放入活动缓存中？


        Log.d(TAG, "saveCahce(): key:" + key + " 保存到磁盘缓存");
        // 保存到磁盘缓存中
        if (diskLruCache != null) {
            diskLruCache.put(key, value);
        }
    }
}
