package com.example.javaadvanced.dispatchevent.ViewpagerListview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 外部拦截法：父容器处理冲突，这里即BadViewPager的onInterceptTouchEvent()方法处理事件冲突
 */
public class MyListView extends ListView {
    private static String TAG = "MyListView";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
