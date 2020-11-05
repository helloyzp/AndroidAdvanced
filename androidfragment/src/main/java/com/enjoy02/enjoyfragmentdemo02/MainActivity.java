package com.enjoy02.enjoyfragmentdemo02;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
    private String TAG  = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);



        if(savedInstanceState == null){
            //1.获取fragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //2.开启一个fragment事务
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //3.向FrameLayout容器添加MainFragment,现在并未真正执行
            transaction.add(R.id.frameLayout, MainFragment.newIntance(), MainFragment.class.getName());


            // 4.提交事务，真正去执行添加动作
            transaction.commit();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
