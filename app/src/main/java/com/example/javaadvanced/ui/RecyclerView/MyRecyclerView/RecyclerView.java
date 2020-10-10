package com.example.javaadvanced.ui.RecyclerView.MyRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

    private String TAG = "RecyclerView";

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


    private int scrollY; //偏移距离,屏幕中第一个可见item距离距离屏幕左上角的距离
    private int firstRow;//屏幕中第一个可见item在数据集中的索引

    public RecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
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
                if (distanceY > touchSlop) {//滑动大于touchSlop即认为产生了滑动
                    intercept = true;
                }
                break;
            }
        }


        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                float y = event.getRawY();
                float diff = currentY - y;//注意是第一个点-第二个点(最新的点)
                scrollBy(0, (int) diff);
                break;
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 因为调用系统的scrollBy()方法，只是滑动当前的MyRecyclerView容器的画布canvas
     * 我们需要在滑动的时候，动态的删除和加入子view，所以重写系统的scrollBy()方法
     *
     * @param x
     * @param y y方向滑动的距离,上滑是正数 下滑是负数
     */
    @Override
    public void scrollBy(int x, int y) {
        //super.scrollBy(x, y);View的scrollBy()滑动的是画布canvas，所以不能直接调用super.scrollBy(x, y)

        scrollY += y;

        //滑动临界值的情况，比如第一条数据已经在屏幕第一条item的位置还要继续下滑,或者最后一条数据已经在屏幕的最后一条item的位置还要继续上滑
        scrollY = scrollBounds(scrollY);

        if (scrollY > 0) {//往上滑
            // <1> 上滑移除最上面的一条
            while (heights[firstRow] < scrollY) {//当往上滑动的距离大于屏幕中第一个item的高度，则将屏幕中第一个item移除
                if (!viewList.isEmpty()) {
                    //移除最上面的itemView
                    Log.i(TAG, "-------------从RecyclerView中移除itemView-------------position=" + firstRow);
                    removeView(viewList.remove(0));

                }
                scrollY -= heights[firstRow];
                firstRow++;
            }

            // <2>上滑加载最下面的一条
            // 当剩下的数据的总高度小于屏幕的高度的时候，最下面添加一条数据
            while (getFillHeight() < height) {
                int addLast = firstRow + viewList.size();
                View view = obtain(addLast, width, heights[addLast]);
                //上滑时往viewList中添加数据
                viewList.add(viewList.size(), view);
            }

        } else { //往下滑

            //<3>下滑加载最上面的一条
            while (scrollY < 0) {  //这里判断scrollY<0即可，滑到顶置零
                //第一行应该变成firstRow - 1
                int firstAddRow = firstRow - 1;
                View view = obtain(firstAddRow, width, heights[firstAddRow]);
                viewList.add(0, view);//添加到第一行
                firstRow--;
                scrollY += heights[firstRow + 1];
            }

            //<4>下滑移除最下面的一条
            while (sumArray(heights, firstRow, viewList.size()) - scrollY - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }

        }

        //重新布局
        rePositionViews();

    }

    private int getFillHeight() {
        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    private void rePositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = 0;
        for (View view : viewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }

    }

    private int scrollBounds(int scrollY) {
        if (scrollY > 0) {
            //上滑极限值
            scrollY = Math.min(scrollY, sumArray(heights, firstRow, heights.length - firstRow) - height);
        } else {
            //下滑极限值
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));
        }
        return scrollY;

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
        if (recycledView == null) { //如果缓存池中没有，则调用adapter.onCreateViewHolder()创建
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
        view.setTag(R.id.tag_viewtype, type);//removeView时需要加入缓存池中，需要用到type

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

        /**
         * 缓存池
         * 每种类型的View对应一个Stack，所以用数组
         */
        private Stack<View>[] views;


        /**
         * @param typeCount 几种类型的itemView
         */
        public Recycler(int typeCount) {
            views = new Stack[typeCount];
            for (int i = 0; i < typeCount; i++) {
                views[i] = new Stack<>();
            }
        }

        /**
         * 从缓存中读取
         *
         * @param viewType
         * @return
         */
        public View getRecycledView(int viewType) {
            Log.i(TAG, "getRecycledView(),viewType=" + viewType);
            View view = null;
            try {
                view = views[viewType].pop();
            } catch (Exception e) {//可能存在缓存池中没有View的情况
                e.printStackTrace();
                view = null;
            }
            return view;
        }

        /**
         * 添加到缓存中
         *
         * @param view
         * @param viewType
         */
        public void addRecycledView(View view, int viewType) {
            Log.i(TAG, "addRecycledView(),viewType=" + viewType);
            views[viewType].push(view);
            Log.i(TAG, "addRecycledView(),viewType=" + viewType + " , views[viewType].size()=" + views[viewType].size());
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
