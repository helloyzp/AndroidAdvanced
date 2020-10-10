package com.example.javaadvanced.ui.RecyclerView.MyRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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

    public RecyclerView(Context context) {
        super(context);
        init();
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        needReLayout = true;
    }

    public void setAdapter(Adapter adapter) {
        needReLayout = true;
        this.adapter = adapter;

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
                for (int i = 0; i < rowCount; i++) {
                    //依赖这个方法测量item的高度
                    heights[i] += adapter.getHeight(i);


                }

                width = r-l;
                height = b-t;

                int top = 0, bottom;



                for (int i = 0; i < rowCount && top < height; i++) {
                    bottom = top + heights[i];

                    //实例化，布局
                    //怎么摆放
                    //摆放多少个

                    View view = makeAndSetup(i, 0,top, width, bottom) ;
                    addView(view);
                    viewList.add(view);
                    top = bottom;
                }







            }

        }

    }

    private View makeAndSetup(int indexOfData, int left, int top, int right,int bottom) {
        View view = obtain(indexOfData, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtain(int row, int width, int height) {
        return adapter.onCreateViewHolder(row, null, this);
    }


    public final class Recycler {

        private Stack<View>[] views;//每种类型的View对应一个Stack，所以用数组


        /**
         *
         * @param count 几种类型的itemView
         */
        public Recycler(int count) {
            views = new Stack[count];
            for(int i=0; i<count;i++) {
                views[i] = new Stack<>();
            }
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
