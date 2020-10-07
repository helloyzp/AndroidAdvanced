package com.example.javaadvanced.dispatchevent.ViewpagerListview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内部拦截法：子view处理事件冲突，这里即MyListView的dispatchTouchEvent()方法处理事件冲突
 */
public class ViewpagerListviewActivity2 extends AppCompatActivity {

    private int[] iv = new int[]{R.mipmap.iv_0, R.mipmap.iv_1, R.mipmap.iv_2,
            R.mipmap.iv_3, R.mipmap.iv_4, R.mipmap.iv_5,
            R.mipmap.iv_6, R.mipmap.iv_7, R.mipmap.iv_8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        MyViewPager2 viewPager = findViewById(R.id.viewpager);

        List<Map<String, Integer>> strings = new ArrayList<>();

        Map<String, Integer> map;

        for (int i = 0; i < 20; i++) {
            map = new HashMap<>();
            map.put("key", iv[i % 9]);
            strings.add(map);
        }

        MyPagerAdapter2 adapter = new MyPagerAdapter2(this, strings);
        viewPager.setAdapter(adapter);
    }
}
