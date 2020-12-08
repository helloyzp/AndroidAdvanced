package com.example.leakcanarydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Zero",this.getClass().getSimpleName() + " onCreate...");
        setContentView(R.layout.activity_test);

        TestDataModel.getInstance().activities.add(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Zero",this.getClass().getSimpleName() + " onStart...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Zero",this.getClass().getSimpleName() + " onRestart...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Zero",this.getClass().getSimpleName() + " onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Zero",this.getClass().getSimpleName() + " onPause...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Zero",this.getClass().getSimpleName() + " onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Zero",this.getClass().getSimpleName() + " onDestroy...");
    }
}