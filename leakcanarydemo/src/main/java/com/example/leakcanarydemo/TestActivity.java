package com.example.leakcanarydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * 单例对象持有Activity的实例，演示内存泄漏
 */
public class TestActivity extends AppCompatActivity {
    private String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this.getClass().getSimpleName() + " onCreate()...");
        setContentView(R.layout.activity_test);

        TestDataModel.getInstance().addActivity(this);
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
        Log.i("TAG", this.getClass().getSimpleName() + " onResume()...");
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