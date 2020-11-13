package com.example.javaadvanced.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 进入测试页面后只要点击下页面就会创建线程，而线程会进入阻塞队列不会结束。
 *
 * 我们知道创建线程是一个重量级的操作，java线程被映射为OS线程，不仅会引起OS调用（即上下文切换）而进入内核空间，
 * 而且在用户空间的消耗也是很大的，在JVM中，每个线程都有相对较大的栈内存(默认是256 KB，可通过-Xss参数设置)。
 *
 * 这个页面就是测试每个线程的资源消耗。
 *
 */
public class ThreadTestActivity extends Activity implements View.OnClickListener {

    private ReentrantLock lock;
    private int count = 0;

    private int THREAD_COUNT_PerClick = 100;//每次点击时创建的线程数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView view = new TextView(this);
        view.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        view.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        view.setTextColor(getResources().getColor(android.R.color.black));
        view.setText("hello");
        view.setOnClickListener(this);
        setContentView(view);
        lock = new ReentrantLock();
        lock.lock();
    }

    @Override
    public void onClick(View v) {
        count++;
        StringBuilder stringBuilder = new StringBuilder(count + "\r\n");

        Runtime runtime = Runtime.getRuntime();

        stringBuilder.append("TotalMemory:").append(getNum(runtime.totalMemory())).append("\r\n");
        long startFreeMemory = runtime.freeMemory();
        stringBuilder.append("FreeMemory:").append(getNum(startFreeMemory)).append("\r\n");

        long startTime = Debug.threadCpuTimeNanos();

        for (int i = 0; i < THREAD_COUNT_PerClick; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }.start();
        }


        long endTime = Debug.threadCpuTimeNanos();

        stringBuilder.append("used Time:").append((endTime - startTime) + " 纳秒").append("\r\n");
        long endFreeMemory = runtime.freeMemory();
        long usedMemory = startFreeMemory - endFreeMemory;
        stringBuilder.append("used Memory:").append(getNum(usedMemory))
                .append(" ，每个线程used Memory:" + getNum(usedMemory/ THREAD_COUNT_PerClick))
                .append("\r\n");

/*
        stringBuilder.append("TotalMemory:").append(getNum(runtime.totalMemory())).append("\r\n");
        stringBuilder.append("FreeMemory:").append(getNum(runtime.freeMemory())).append("\r\n");

        startTime = Debug.threadCpuTimeNanos();
        for (int i = 0; i < 1000; ) {
            i++;
        }
        endTime = Debug.threadCpuTimeNanos();
        stringBuilder.append("used Time:").append((endTime - startTime) + " 纳秒").append("\r\n");*/

        ((TextView) v).setText(stringBuilder.toString());

    }


    /**
     * Byte转KB
     * @param totalMemory 单位是字节
     * @return 单位是KB
     */
    private String getNum(long totalMemory) {

        return (totalMemory / 1024) + "KB";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lock.unlock();
        count = 0;
    }


}
