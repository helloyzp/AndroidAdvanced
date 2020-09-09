package com.example.javaadvanced.viewpagerheightwrap;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        Log.d(TAG, "onMeasure: getChildCount: " + getChildCount());
        //遍历所有的子View，用传递过来的MeasureSpec以及子View的LayoutParams测量子View的高度，采用最高的子View的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            /**
             * 网上流行的解决方法，但是是错误的解决方法
             * 这种解决方法并不会真正的测量子View的高度
             */
            //child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));

            //正确的解决方法:要想测量子View的大小，必须获取子View的LayoutParams，然后结合父布局传递来的MeasureSpec进行测量
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            int childHightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthSpec, childHightSpec);

            int h = child.getMeasuredHeight();
            if (h > height) {//采用最高的子View的高度
                height = h;
            }
            Log.d(TAG, "onMeasure: " + h + " height: " + height);
        }
        //测量出孩子的高度后用这个高度计算自己的heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        //这样调用super.onMeasure(widthMeasureSpec, heightMeasureSpec);之后ViewPager设置的高度就是自己子View的高度，而不是父容器的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            ViewGroup.LayoutParams lp =  child.getLayoutParams();
//            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec,0,lp.width);
//            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec,0,lp.height);
//            child.measure(childWidthSpec,childHeightSpec);
//        }
//
//        int height = 0;
//        switch (heightMode) {
//            case MeasureSpec.EXACTLY:
//                height = heightSize;
//                break;
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.UNSPECIFIED:
//                for (int i = 0; i < getChildCount(); i++) {
//                    View child = getChildAt(i);
//                    height = Math.max(height,child.getMeasuredHeight());
//                }
//                break;
//            default:
//                break;
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


}
