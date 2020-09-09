package com.example.javaadvanced.viewpagerheightwrap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.javaadvanced.R;


/**
 * @author 享学课堂 Alvin
 * @package com.xiangxue.alvin.viewpagerwrap
 * @fileName MyPagerAdapter
 * @date on 2019/7/p3
 * @qq 2464061231
 **/
public class MyPagerAdapter extends PagerAdapter {
    private static final String TAG = "MyPagerAdapter";
    private List<Integer> mImages;
    private Context mContext;

    MyPagerAdapter(List<Integer> images, Context context) {
        mImages = images;
        mContext = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mImages.size();
        Log.i(TAG, "instantiateItem(), container=" + container);//container=com.example.javaadvanced.viewpagerheightwrap.MyViewPager
        //View view = LayoutInflater.from(mContext).inflate(R.layout.linear_item, null);
        //这里如果attachToRoot设置为true，则inflate()方法返回的是MyViewPager；如果设置为fasle，则返回的是item布局的根View
        View view = LayoutInflater.from(mContext).inflate(R.layout.linear_item, container, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(position + " ");
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
