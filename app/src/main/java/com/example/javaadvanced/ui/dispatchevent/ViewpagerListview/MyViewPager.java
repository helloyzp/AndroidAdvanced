package com.example.javaadvanced.ui.dispatchevent.ViewpagerListview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager的每个item是ListView
 * 预期的效果是：上下滑动时可以滑动ListView，左右滑动时可以滑动ViewPager
 *
 * 外部拦截法：父容器处理冲突，这里即BadViewPager的onInterceptTouchEvent()方法处理事件冲突
 */
public class MyViewPager extends ViewPager {

    private static String TAG = "MyListView2";

    private int mLastX, mLastY;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

/*
    //错误写法1: 事件都被ViewPager拦截了，所以ListView不会收到任何事件，只能ViewPager滑动，ListView不能正常滑动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
       Log.i(TAG, "onInterceptTouchEvent(), event=" + event);

        return true;
    }
    */

/*
    //错误写法2:  ViewPager不会消费事件，因此ViewPager不能左右滑动，只能ListView进行上下滑动
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i(TAG, "onInterceptTouchEvent(), event=" + event);

        return false;
    }
    */


/*
    //正确写法1：直接调用父类ViewPager的onInterceptTouchEvent()方法，因为google工程师已经在ViewPager的onInterceptTouchEvent()方法里对事件进行了处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }
*/


    // 正确写法2：
    // 外部拦截法：父容器处理冲突，这里即BadViewPager的onInterceptTouchEvent()方法处理事件冲突
    // 我想要把事件分发给谁就分发给谁
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {//X方向滑动距离大于y方向滑动距离，则进行拦截事件，把事件交给ViewPager
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent(), ev=" + ev);
        return super.onTouchEvent(ev);
    }

}
