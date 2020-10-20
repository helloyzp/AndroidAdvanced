package com.example.javaadvanced.performanceOptimization.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

import com.example.javaadvanced.R;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;


public class FirstActivity extends Activity {

    private int i = 10;//成员属性i

    private Handler mHandler = new Handler();

    /**
     * 如果不传外部类引用则不能访问外部类的成员属性i
     */
    static class R1 implements Runnable {

        @Override
        public void run() {
            //但是静态内部类不能访问外部类的成员属性i，怎么办？将外部类引用传递给静态内部类
            //System.out.println("i=" + i);
        }
    }

    /**
     * 如果直接传递外部类引用则依然会造成内存泄露
     */
    static class R2 implements Runnable {
        //但是不能直接将外部类引用传递进来，因为虽然可以访问外部类的成员属性，但是还是会造成内存泄露
        FirstActivity activity;

        public R2(FirstActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            System.out.println(activity.i);
        }
    }



    /**
     *
     * 正确解决方案：使用弱引用，即静态内部类持有外部类的弱引用
     *
     */
    static class R3 implements Runnable {
        /**
         * 注意这里应该使用WeakReference，而不是SoftReference，
         * 虽然使用SoftReference也不会造成OOM，但是当我们退出Activity时是希望Activity尽快被回收的，
         * 所以使用WeakReference更合适，因为被WeakReference关联的对象在GC执行时会被直接回收，
         * 而对于SoftReference关联的对象，GC不会直接回收，而是在系统将要内存溢出之前才会触发GC将这些对象进行回收。
         */
        WeakReference<FirstActivity> activityWeakReference;

        public R3(FirstActivity activity) {
            this.activityWeakReference = new WeakReference<FirstActivity>(activity);
        }

        @Override
        public void run() {
            if(activityWeakReference.get() != null) {
                int i = activityWeakReference.get().i;
                System.out.println("i=" + i);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencond);



        //gc root

        /**
         * Handler的内存泄露场景
         */
        mHandler.postDelayed(new Runnable() {//这里创建了一个匿名内部类对象

            @Override
            public void run() {
                //匿名内部类对象持有外部类的引用(即持有Activity的引用)，所以可以直接访问外部类的的成员属性i
                //所以会造成内存泄露
                System.out.println("i=" + i);
            }
        },1_000);


        //解决方案1：在onDestroy()方法里调用 mHandler.removeCallbacksAndMessages(null);将消息队列里的Message全部移除

        //解决方案2：使用静态内部类，静态内部类不会持有外部类的引用，而内部类和匿名内部类会持有外部类的引用
        mHandler.postDelayed(new R3(this), 10_000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //解决方案1：在onDestroy()方法里调用 mHandler.removeCallbacksAndMessages(null);将消息队列里的Message全部移除
        mHandler.removeCallbacksAndMessages(null);


        InputMethodManagerGC();
    }

    private void InputMethodManagerGC() {
        // 系统BUG：断掉 gc root
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            Field mCurRootView = InputMethodManager.class.getDeclaredField("mCurRootView");
            mCurRootView.setAccessible(true);
            mCurRootView.set(im, null);

            Field mNextServedView = InputMethodManager.class.getDeclaredField("mNextServedView");
            mNextServedView.setAccessible(true);
            mNextServedView.set(im, null);

            Field mServedView = InputMethodManager.class.getDeclaredField("mServedView");
            mServedView.setAccessible(true);
            mServedView.set(im, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
