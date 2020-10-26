package com.example.javaadvanced.performanceOptimization.apkshrink.resguard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.javaadvanced.R;

/**
 * 资源混淆
 */
public class ResguardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resguard);


        ImageView iv = findViewById(R.id.iv);
        iv.setImageResource(R.drawable.a);
    }
}
