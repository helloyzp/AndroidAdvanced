package com.example.javaadvanced.performanceOptimization.anr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class ANRMainActivity extends AppCompatActivity {

    private static final String TAG = "ANRMainActivity";

    private Handler mHandler = new Handler();

    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_main);
        textView = findViewById(R.id.textview);
        button  = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ANRMainActivity.this, ANRSecondActivity.class);
                startActivity(intent);
            }
        });

        //ActivityANRTest();
        //anrTest();
    }

    /**
     * 主线程休眠比较长的一段时间演示ANR
     */
    public void ActivityANRTest() {
/*        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("anr");
            }
        }, 1000 * 3);//延迟3秒发送一个更新UI的执行任务

        SystemClock.sleep(1000 * 50);//睡眠30秒
    }

    /**
     * WatchDog的使用
     */
    public void anrTest() {
        ANRWatchDog.getInstance().addANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAnrHappened(String stackTraceInfo) {
                Log.i(TAG, stackTraceInfo);
            }
        });
        //启动WatchDog
        ANRWatchDog.getInstance().start();
        //延迟发送一个休眠任务
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }, 3000);

    }


    @Override
    protected void onPause() {
        super.onPause();

        try {
            Thread.sleep(1000 * 9);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

/*        try {
            Thread.sleep(1000 * 9);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}
