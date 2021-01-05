package com.example.javaadvanced.performanceOptimization.anr;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class ANRSecondActivity extends AppCompatActivity {

    private static final String TAG = "ANRMainActivity";

    private Handler mHandler = new Handler();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_second);
        textView = findViewById(R.id.textview);

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


}
