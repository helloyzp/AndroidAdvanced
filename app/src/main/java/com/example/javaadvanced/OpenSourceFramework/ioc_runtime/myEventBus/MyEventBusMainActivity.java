package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.myEventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.javaadvanced.R;


/**
 * 手写MyEventBus，实现EventBus的核心原理
 */
public class MyEventBusMainActivity extends Activity {
    private String TAG = "MyEventBusMainActivity";

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myeventbus_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyEventBusMainActivity.this, MyEventBusSecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //register()方法会在参数activity中找到所有标注了@Subscribe注解的方法保存到EventBus中
        MyEventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
        MyEventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void clickEvent(ClickMessageEvent clickMessageEvent) {

        Toast.makeText(this, "MyEventBusMainActivity接收到点击事件", Toast.LENGTH_SHORT).show();

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void threadEvent(ThreadMessageEvent threadMessageEvent) {
        Log.i(TAG, "threadEvent(), MyEventBusMainActivity接收到threadMode事件, thread=" + Thread.currentThread().getName());

    }

}
