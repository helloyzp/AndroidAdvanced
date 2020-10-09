package com.example.javaadvanced.ui.RecyclerView.slidecard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.javaadvanced.R;
import com.example.javaadvanced.ui.RecyclerView.slidecard.adapter.UniversalAdapter;
import com.example.javaadvanced.ui.RecyclerView.slidecard.adapter.ViewHolder;

import java.util.List;

/**
 * RecyclerView实现层叠卡片滑动删除特效，
 * 学习自定义RecyclerView.LayoutManager
 */
public class SlidecardActivity extends AppCompatActivity {

    private RecyclerView rv;
    private UniversalAdapter<SlideCardBean> adapter;
    private List<SlideCardBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidecard);

        rv = findViewById(R.id.rv);
        //rv.setLayoutManager(new LinearLayoutManager(this));//使用LinearLayoutManager效果是线性排列的，达不到想要的效果
        rv.setLayoutManager(new SlideCardLayoutManager());

        mDatas = SlideCardBean.initDatas();
        adapter = new UniversalAdapter<SlideCardBean>(this, mDatas, R.layout.item_swipe_card) {

            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
                viewHolder.setText(R.id.tvName, slideCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, slideCardBean.getPostition() + "/" + mDatas.size());
                Glide.with(SlidecardActivity.this)
                        .load(slideCardBean.getUrl())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };
        rv.setAdapter(adapter);
        // 初始化数据
        CardConfig.initConfig(this);

        //拖动卡片的滑动效果
        SlideCallback slideCallback = new SlideCallback(rv, adapter, mDatas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(slideCallback);
        itemTouchHelper.attachToRecyclerView(rv);

    }
}
