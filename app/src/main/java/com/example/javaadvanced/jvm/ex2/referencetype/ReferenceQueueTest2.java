package com.example.javaadvanced.jvm.ex2.referencetype;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 我们希望当一个对象被gc掉的时候通知用户线程，进行额外的处理时，就需要使用引用队列了。
 * 当一个对象被gc掉之后，该对象关联的引用对象(SoftReference,WeakReference,PhantomReference)
 * 会被放入ReferenceQueue中。
 * 我们可以从ReferenceQueue中获取到相应的对象信息，同时进行额外的处理。比如反向操作，数据清理等。
 * <p>
 * <p>
 * 设置虚拟机参数：-Xms20m -Xmx20m
 */
public class ReferenceQueueTest2 {
    private static int _1M = 1024 * 1024;

    private static ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<byte[]>();

    public static void main(String[] args) {


        ArrayList<Reference> referenceArrayList = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("run()...");
                try {
                    int cnt = 0;
                    WeakReference<byte[]> k;
                    while ((k = (WeakReference) referenceQueue.remove()) != null) {
                        System.out.println((cnt++) + "回收了:" + k);
                    }
                } catch (InterruptedException e) {
                    //结束循环
                    e.printStackTrace();
                }
                System.out.println("run()结束");
            }
        };
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 50; i++) {//循环50次，总共创建了50M的字节数组，超过了设置的虚拟机内存20M

            //weakReference引用对象与创建的数组对象是弱引用关系
            WeakReference<byte[]> weakReference = new WeakReference<byte[]>(new byte[_1M], referenceQueue);
            /*每一个byte数组都对应有一个WeakReference指向它，为了测试ReferenceQueue的用法，将所有的WeakReference用一个List保存，
            否则当内存不足时WeakReference对象会被回收，从而当回收字节数组对象时，其对应的WeakReference对象已经被回收，
            该WeakReference对象就不会进入ReferenceQueue了。
             */
            referenceArrayList.add(weakReference);
        }

    }

}
