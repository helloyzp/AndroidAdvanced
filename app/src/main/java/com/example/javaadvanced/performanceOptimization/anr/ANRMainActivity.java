package com.example.javaadvanced.performanceOptimization.anr;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class ANRMainActivity extends AppCompatActivity {

    private static final String TAG = "ANRMainActivity";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anr);

        anrTest();
    }

    void anrTest() {
        ANRWatchDog.getInstance().addANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAnrHappened(String stackTraceInfo) {
                Log.i(TAG, stackTraceInfo);
            }
        });

        ANRWatchDog.getInstance().start();
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
