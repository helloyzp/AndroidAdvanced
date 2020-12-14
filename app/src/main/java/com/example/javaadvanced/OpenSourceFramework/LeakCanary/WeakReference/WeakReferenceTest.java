package com.example.javaadvanced.OpenSourceFramework.LeakCanary.WeakReference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * WeakReference的使用：
 * WeakReference和ReferenceQueue结合使用，监控某个对象是否被gc回收。
 * 只要在ReferenceQueue中有弱引用对象，则该弱引用对象引用的对象一定被GC回收了。
 */
public class WeakReferenceTest {
    /**
     * WeakReference和ReferenceQueue结合使用的例子：用来监控某个对象是否被gc回收。
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("hello world");

        ReferenceQueue referenceQueue = new ReferenceQueue();

        Object obj = new Object();

        //把obj放入weakReference，并和一个referenceQueue关联
        //当obj被gc回收后，指向它的weakReference会被添加到与之关联的referenceQueue
        WeakReference weakReference = new WeakReference(obj, referenceQueue);
        System.out.println("盛放obj的weakReference = " + weakReference);

        //把obj置空，让它没有强引用
        obj = null;

        Runtime.getRuntime().gc();//gc，让可以回收的对象回收

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        Reference findRef = null;

        do {
            findRef = referenceQueue.poll();
            //如果能找到上面的weakReference => 说明它引用的obj被gc回收了
            System.out.println("findRef = " + findRef + "是否等于上面的weakReference = " + (findRef == weakReference));
        } while (findRef != null);//把所有放到referenceQueue的引用找出来

    }
}
