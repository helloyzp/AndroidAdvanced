package com.example.javaadvanced.OpenSourceFramework.ioc_runtime.myEventBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javaadvanced.R;


public class MyEventBusSecondActivity extends Activity {
    private String TAG = "MyEventBusSecondActivity";

    private Button button;
    private Button buttonThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myeventbus_second);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEventBus.getDefault().post(new ClickMessageEvent());
            }
        });

        buttonThread = findViewById(R.id.buttonThread);
        buttonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEventBus.getDefault().post(new ThreadMessageEvent());
            }
        });

    }


}
