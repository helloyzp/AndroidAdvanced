package com.example.javaadvanced.dispatchevent.SwipeRefreshLayoutViewpaegr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.javaadvanced.R;


/**
 * 解决SwipeRefreshLayout嵌套ViewPager时ViewPager不能水平滑动的问题
 */
public class SRL_VP_mainActivity extends AppCompatActivity {

    private CustomSRL2 swipeRefreshLayout;
    private CustomVPInner viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srl_vp);
        initView();
        initData();
    }

    private void initView() {
        swipeRefreshLayout = (CustomSRL2) findViewById(R.id.swipeRefreshLayout);
        viewPager = (CustomVPInner) findViewById(R.id.viewPager);
    }

    private void initData() {
        viewPager.setAdapter(new SubVpAdapter(getSupportFragmentManager(), DataModel.getFragmentList1()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
