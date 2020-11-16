package com.example.javaadvanced;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.network.okhttp.OkhttpMainActivity;
import com.example.javaadvanced.thread.ThreadTestActivity;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testClassLoader();
        getVMStackClassLoader();

        gotoActivity();
    }

    private void testClassLoader() {

        ClassLoader ActivityClassLoader= Activity.class.getClassLoader();
        Log.i(TAG, "Activity, ActivityClassLoader=" + ActivityClassLoader);//java.lang.BootClassLoader@c4f388e

        ClassLoader MainActivityClassLoader = MainActivity.this.getClass().getClassLoader();
        Log.i(TAG, "MainActivity, MainActivityClassLoader=" + MainActivityClassLoader);//dalvik.system.PathClassLoader
    }

    /**
     * VMStack是一个虚拟机栈，在Android系统中，每个应用都有一个独立的虚拟机，
     * 所以VMStack.getCallingClassLoader()是获取当前应用的ClassLoader，
     *
     * Class.forName(String className)方法就是通过VMStack.getCallingClassLoader()获取类加载器的
     */
    private void getVMStackClassLoader() {
        try {
            Class<?> cls = Class.forName("dalvik.system.VMStack");
            Method getCallingClassLoader = cls.getMethod("getCallingClassLoader");
            getCallingClassLoader.setAccessible(true);
            Object invoke = getCallingClassLoader.invoke(null);
            Log.i(TAG, "getVMStackClassLoader(): " + invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoActivity() {
        //Intent intent = new Intent(this, MemoryMainActivity.class);
        //Intent intent = new Intent(this, HTTPRequestActivity.class);
        //Intent intent = new Intent(this, FacadeModelActivity.class);
        //Intent intent = new Intent(this, ProxyModelActivity.class);
        //Intent intent = new Intent(this, BitmapCacheActivity.class);
        //Intent intent = new Intent(this, BitmapCacheActivity.class);
        //Intent intent = new Intent(this, ApkShrinkActivity.class);
        //Intent intent = new Intent(this, ResguardActivity.class);
        //Intent intent = new Intent(this, ScreenadapterActivity.class);
        Intent intent = new Intent(this, OkhttpMainActivity.class);
        //Intent intent = new Intent(this, ThreadTestActivity.class);
        startActivity(intent);
    }

}
