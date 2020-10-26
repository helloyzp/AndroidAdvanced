package com.example.javaadvanced.performanceOptimization.apkshrink;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class ApkShrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apkshrink);

        //动态获取资源id的方式:
        getResources().getIdentifier("activity_apkshrink1", "layout", getPackageName());

    }
}
