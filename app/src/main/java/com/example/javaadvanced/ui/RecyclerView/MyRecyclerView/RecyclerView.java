package com.example.javaadvanced.ui.RecyclerView.MyRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.javaadvanced.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 自定义RecyclerView，理解RecyclerView的核心原理
 */
public class RecyclerView extends ViewGroup {

    private boolean needReLayout;

    /**
     * 缓存屏幕内的View的集合
     */
    private List<View> viewList;

    private Recycler recycler;

    private Adapter adapter;

    private int rowCount;

    //每一行的高度
    private int[] heights;

    private int width;
    private int height;

    private int touchSlop;//最小滑动距离

    private float currentY;

    public RecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context
    ) {
        needReLayout = true;
        viewList = new ArrayList<>();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            needReLayout = true;
            this.adapter = adapter;
            this.recycler = new Recycler(adapter.getViewTypeCount());
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = ev.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float distanceY = Math.abs(ev.getRawY() - currentY);
                if(distanceY > touchSlop) {//认为产生了滑动
                    intercept = true;
                }
                break;
            }
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (needReLayout || changed) {
            needReLayout = false;

            //布局的时候初始化
            viewList.clear();
            //比较耗时
            removeAllViews();
            if (adapter != null) {
                rowCount = adapter.getCount();
                heights = new int[rowCount];
                for (int i = 0; i < rowCount; i++) {
                    //依赖这个方法测量item的高度
                    heights[i] = adapter.getHeight(i);
                }

                width = r - l;
                height = b - t;


                /**
                 * RecyclerView在填充item的过程中，每填充一个item，RecyclerView的bottom都会加上这个item的高度，
                 * 直到RecyclerView的bottom大于屏幕的高度，填充完毕。
                 */
                int top = 0, bottom;
                for (int i = 0; i < rowCount && top < height; i++) {
                    bottom = top + heights[i];

                    //实例化，布局
                    //怎么摆放
                    //摆放多少个

                    View view = makeAndSetup(i, 0, top, width, bottom);
                    viewList.add(view);
                    top = bottom;
                }


            }

        }

    }

    private View makeAndSetup(int indexOfData, int left, int top, int right, int bottom) {
        View view = obtain(indexOfData, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtain(int row, int width, int height) {
        int type = adapter.getItemViewType(row);

        View view = null;
        View recycledView = recycler.getRecycledView(type);
        if (recycledView == null) { //缓存池中没有则调用adapter.onCreateViewHolder()创建
            view = adapter.onCreateViewHolder(row, null, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHolder() 必须初始化.");
            }
        } else {
            view = adapter.onBindViewHolder(row, recycledView, this);
        }
        if (view == null) {
            throw new RuntimeException("convertView can not be null.");
        }
        view.setTag(R.id.tag_viewtype, type);

        //测量
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        addView(view, 0);//每次添加View到第一个元素

        return view;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);

        //View从RecyclerView移除时放入缓存池中
        int type = (int) view.getTag(R.id.tag_viewtype);
        recycler.addRecycledView(view, type);

    }

    public final class Recycler {

        private Stack<View>[] views;//每种类型的View对应一个Stack，所以用数组


        /**
         * @param typeCount 几种类型的itemView
         */
        public Recycler(int typeCount) {
            views = new Stack[typeCount];
            for (int i = 0; i < typeCount; i++) {
                views[i] = new Stack<>();
            }
        }

        public View getRecycledView(int viewType) {
            View view = null;
            try {
                views[viewType].pop();
            } catch (Exception e) {//可能存在缓存池中没有View的情况
                e.printStackTrace();
                view = null;
            }
            return view;
        }

        public void addRecycledView(View view, int viewType) {
            views[viewType].push(view);
        }


    }


    public abstract static class Adapter {

        //public abstract View onCreateViewHolder(ViewGroup parent, int viewType);

        //public abstract void onBindViewHolder(View view, int position);

        public abstract View onCreateViewHolder(int position, View convertView, ViewGroup parent);

        public abstract View onBindViewHolder(int position, View convertView, ViewGroup parent);

        /**
         * item的类型
         *
         * @param position
         * @return
         */
        public abstract int getItemViewType(int position);

        /**
         * item的类型的数量
         *
         * @return
         */
        public abstract int getViewTypeCount();

        public long getItemId(int position) {
            return NO_ID;
        }

        /**
         * @return
         */
        public abstract int getCount();

        /**
         * item的高度
         *
         * @param position
         * @return
         */
        public abstract int getHeight(int position);
    }

}
