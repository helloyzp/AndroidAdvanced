package com.example.javaadvanced.OpenSourceFramework.LeakCanary.myleakcanary;

public class leakcanaryTest {

    public static void main(String[] args) {

        Watcher watcher = new Watcher();

        Object obj = new Object();
        System.out.println("obj: " + obj);
        watcher.watch(obj,"");
        Utils.sleep(500);
        //释放对象
        obj = null;
        Utils.gc();
        //TODO: 思考如何判断被观察的对象可能存在内存泄漏?
        // 答：查看retainedReferences队列中是否有这个对象对应的弱引用对象即可。

        Utils.sleep(5000);
        System.out.println("查看是否在怀疑列表中：" + watcher.getRetainedReferences().size());

        //要确定retainedReferences中的对象是否发生了内存泄漏需要进行可达性分析

    }

}
