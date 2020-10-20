package com.example.javaadvanced.performanceOptimization.memory;

import android.content.Context;

/**
 * 内存泄漏问题常见场景
 * 静态成员/单例：作为GC ROOT，持有短生命周期对象的引用(如持有Activity对象的引用)导致短生命周期对象无法释放。
 */
public class SingletonManager {
    //GC ROOT：静态属性所引用的对象
    private static final SingletonManager ourInstance = new SingletonManager();

    static String i = "aa";


    private Context mContext;

    public static SingletonManager getInstance() {
        return ourInstance;
    }

    private SingletonManager() {
    }

    /**
     * 解决办法：mContext持有ApplicationContext，而不是持有Activity
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
    }
}
