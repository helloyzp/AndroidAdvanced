package com.example.javaadvanced.performanceOptimization.memory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftReferenceWeakReference {
    public static void main(String[] args) {
        /**
         * 软引用
         */
        Object object = new Object();//object是强引用
        SoftReference<Object> objectSoftReference = new SoftReference<>(object);//objectSoftReference是软引用
        System.out.println("soft:" + objectSoftReference.get());//soft:java.lang.Object@d716361
        object = null;//强引用object置为null


        System.gc();
        System.out.println("soft:" + objectSoftReference.get());//soft:java.lang.Object@d716361

        System.out.println("======================================");

        /**
         * 弱引用
         */
        object = new Object();
        WeakReference<Object> objectWeakReference = new WeakReference<>(object);//objectWeakReference是弱引用
        System.out.println("weak:" + objectWeakReference.get());//weak:java.lang.Object@6ff3c5b5
        object = null;//强引用object置为null


        System.gc();
        System.out.println("weak:" + objectWeakReference.get());//weak:null
    }
}
