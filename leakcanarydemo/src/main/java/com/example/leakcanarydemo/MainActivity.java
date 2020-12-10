package com.example.leakcanarydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.leakcanarydemo.databinding.ActivityMainBinding;

/**
 * 演示内存泄漏：进入TestActivity后然后返回到MainActivity，LeakCanary就会报内存泄漏
 */
public class MainActivity extends AppCompatActivity implements ComponentCallbacks2 {
    private String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TestActivity.class));
        });
    }


    //内存不够
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //主动去释放一些内存

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //低内存 -> app 被杀
        //尽可能的释放内存
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, this.getClass().getSimpleName() + " onStart()...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, this.getClass().getSimpleName() + " onRestart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, this.getClass().getSimpleName() + " onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, this.getClass().getSimpleName() + " onPause()...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, this.getClass().getSimpleName() + " onStop()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, this.getClass().getSimpleName() + " onDestroy()...");
    }
}
