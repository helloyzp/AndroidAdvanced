package com.example.javaadvanced.ui.dispatchevent.ViewpagerListview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 内部拦截法：子view处理事件冲突，这里即MyListView的dispatchTouchEvent()方法处理事件冲突。
 *
 * 这个例子中，子view就是MyListView2，父布局就是MyViewPager2
 */
public class MyListView2 extends ListView {
    private static String TAG = "MyListView2";

    public MyListView2(Context context) {
        super(context);
    }

    public MyListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //内部拦截法：子view处理事件冲突
    private int mLastX, mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                /**
                 DOWN事件时要设置不让父布局拦截事件，子View一旦拿到DOWN事件，后面的事件由谁处理是由子View决定的。
                 这样后续的MOVE事件都会分发给子View。
                */
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //如果是水平滑动，则设置可以让父布局拦截事件，这样父布局就会拦截掉后续的MOVE事件，从而把水平滑动交给父布局处理。
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;

            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }
}
