package com.example.javaadvanced.dispatchevent.ViewpagerListview;

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
 * 内部拦截法：子view处理事件冲突，这里即MyListView的dispatchTouchEvent()方法处理事件冲突
 *
 */
public class MyViewPager2 extends ViewPager {

    private static String TAG = "BadViewPager2";

    public MyViewPager2(@NonNull Context context) {
        super(context);
    }

    public MyViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    //内部拦截法：子view处理事件冲突，这里即MyListView的dispatchTouchEvent()方法处理事件冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        /**
         * ViewGroup的dispatchTouchEvent()方法会在ACTION_DOWN事件中清空手势的状态：
         *
         *             // Handle an initial down.
         *             if (actionMasked == MotionEvent.ACTION_DOWN) {
         *                 // Throw away all previous state when starting a new touch gesture.
         *                 // The framework may have dropped the up or cancel event for the previous gesture
         *                 // due to an app switch, ANR, or some other state change.
         *                 cancelAndClearTouchTargets(ev);
         *                 resetTouchState();
         *             }
         *
         *   所以，mGroupFlags会清空FLAG_DISALLOW_INTERCEPT标志位，
         *   所以后面判断disallowIntercept时，disallowIntercept的值是false：
         *
         *              if (actionMasked == MotionEvent.ACTION_DOWN
         *                     || mFirstTouchTarget != null) {
         *                 final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
         *                 if (!disallowIntercept) {
         *                     intercepted = onInterceptTouchEvent(ev);
         *                     ev.setAction(action); // restore action in case it was changed
         *                 } else {
         *                     intercepted = false;
         *                 }
         *             } else {
         *                 // There are no touch targets and this action is not an initial down
         *                 // so this view group continues to intercept touches.
         *                 intercepted = true;
         *             }
         *
         *   所以会调用onInterceptTouchEvent()方法。
         *   所以这里要对ACTION_DOWN事件进行处理，不拦截ACTION_DOWN事件，否则子View拿不到事件。
         */
        if (event.getAction() == MotionEvent.ACTION_DOWN){//不能拦截DOWN事件，将DOWN事件分发给子View
            super.onInterceptTouchEvent(event);
            return false;
        }
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent(), ev=" + ev);
        return super.onTouchEvent(ev);
    }

}
