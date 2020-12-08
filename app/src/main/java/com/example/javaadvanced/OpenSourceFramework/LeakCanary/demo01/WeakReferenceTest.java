package com.example.javaadvanced.OpenSourceFramework.LeakCanary.demo01;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceTest {
    /**
     *  WeakReference和ReferenceQueue联合使用案例
     *  也是用来监控某个对象是否被gc回收的手段
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("hello world");

        ReferenceQueue referenceQueue = new ReferenceQueue();

        Object obj = new Object();

        //把obj放入weakReference，并和一个referenceQueue关联
        //当obj被gc回收后，盛放它的weakReference会被添加与之关联的referenceQueue
        WeakReference weakReference = new WeakReference(obj,referenceQueue);
        System.out.println("盛放obj的weakReference = " + weakReference);

        //把obj置空，让它没有强引用
        obj = null;

        Runtime.getRuntime().gc();//gc，让可以回收的对象回收

        try{
            Thread.sleep(1000);
        }catch (Exception e){}

        Reference findRef = null;

        do{
            findRef = referenceQueue.poll();
            //如果能找到上面的weakReference => 说明它盛放的obj被gc回收了
            System.out.println("findRef = " +findRef + "是否等于上面的weakReference = " + (findRef == weakReference));
        }while(findRef !=null);//把所有放到referenceQueue的引用容器找出来

    }
}
