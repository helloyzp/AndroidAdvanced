package com.example.javaadvanced.jvm.ex2.referencetype;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * PhantomReference及ReferenceQueue
 */
public class ReferenceQueueTest {
    public static boolean isRun = true;

    public static void main(String[] args) throws Exception {
        String abcString = new String("abc");
        System.out.println("新建字符串=" + abcString.getClass() + "@" + abcString.hashCode());
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        new Thread() {
            @Override
            public void run() {
                while (isRun) {
                    PhantomReference phantomReference = (PhantomReference) referenceQueue.poll();
                    if (phantomReference != null) {
                        try {
                            //PhantomReference的referent是不可访问的，所以不能通过get()方法获取referent，只能通过反射获取所引用的对象referent
                            Field referentField = Reference.class.getDeclaredField("referent");
                            referentField.setAccessible(true);
                            Object result = referentField.get(phantomReference);
                            System.out.println("gc collected："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
        PhantomReference<String> stringPhantomReference = new PhantomReference<String>(abcString, referenceQueue);
        abcString = null;//强引用置为null
        System.gc();
        Thread.currentThread().sleep(3000);//gc线程是优先级比较低的线程，所以main线程等待一会儿
        isRun = false;
    }

}
