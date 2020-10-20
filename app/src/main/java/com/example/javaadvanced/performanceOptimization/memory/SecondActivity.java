package com.example.javaadvanced.performanceOptimization.memory;

import android.app.Activity;
import android.os.Bundle;

import com.example.javaadvanced.R;

/**
 * 内存抖动修复后的Activity
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencond);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
