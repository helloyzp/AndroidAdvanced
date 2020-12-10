package com.example.javaadvanced.OpenSourceFramework.LeakCanary.myleakcanary;

public class Utils {


    public static void sleep(long millis) {
        System.out.println("sleep: " + millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void gc() {
        System.out.println("执行gc...");
        //主要这里使用的是Runtime.getRuntime().gc()，不是使用System.gc()，因为System.gc()仅仅是通知系统在合适的时间进行一次垃圾回收操作
        //实际上并不保证一定执行
        Runtime.getRuntime().gc();
        sleep(100);
        System.runFinalization();
    }


}
