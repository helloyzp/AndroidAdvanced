package com.example.javaadvanced.jvm.ex2.referencetype;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 * <p>
 * 幽灵引用，最弱（随时会被回收掉）
 * 垃圾回收的时候收到一个通知，就是为了监控垃圾回收器是否正常工作。
 */
public class TestPhantomRef {

    public static void main(String[] args) throws InterruptedException {
        String string = new String("hello");
        final ReferenceQueue<String> queue = new ReferenceQueue<String>();
        // 创建虚引用，要求必须与一个引用队列关联，当这个虚引用关联的对象被GC回收时，这个虚引用会被加入引用队列
        PhantomReference<String> phantomReference = new PhantomReference<String>(string, queue);
        System.out.println("phantomReference.get()=" + phantomReference.get());//PhantomReference的get()一直是null

        string = null;//去掉强引用，否则这个String对象永远不会被回收
        System.gc();
        System.out.println(queue.remove());

    }
}
