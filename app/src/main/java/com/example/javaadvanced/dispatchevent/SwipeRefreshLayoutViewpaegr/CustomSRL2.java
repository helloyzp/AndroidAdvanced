package com.example.javaadvanced.dispatchevent.SwipeRefreshLayoutViewpaegr;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomSRL2 extends SwipeRefreshLayout {
    public CustomSRL2(Context context) {
        super(context);
    }

    public CustomSRL2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
