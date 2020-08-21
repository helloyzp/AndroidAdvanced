package com.example.javaadvanced.jvm.ex2;

import java.util.LinkedList;
import java.util.List;

/**
 * 演示Stop The World现象
 *
 * VM参数： -XX:+PrintGC
 *
 * 下面时截取的一部分GC信息：
 * 7.712
 * 7.813
 * [GC (Allocation Failure)  251406K->257758K(432640K), 0.0579151 secs]
 * [Full GC (Ergonomics)  257758K->250550K(679424K), 0.1102307 secs]
 * 8.1
 * 8.102
 * 8.202
 *
 * 可以看到正常情况下两次相邻的打印时间间隔时100ms，但是7.813这次之后由于发生了GC，
 * 从而造成了STW，用户线程被暂停，导致接下去的打印时间是8.1(如果没有发生GC，7.813接下去的打印时间应该是7.913左右)
 */
public class StopWorld {

    /*不停地往list中填充数据， 触发GC*/
    public static class FillListThread extends Thread{
        List<byte[]> list = new LinkedList<>();

        @Override
        public void run() {
            try {
                while(true){
                    //避免OOM，当创建的对象达到一定大小时清空list
                    if(list.size()*512/1024/1024>=990){
                        list.clear();
                        System.out.println("list is clear");
                    }
                    byte[] bl;
                    for(int i=0;i<100;i++){
                        bl = new byte[512];//字节数组在堆中分配
                        list.add(bl);//用list保存每个字节数组对象的引用
                    }
                    Thread.sleep(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*每100ms定时打印*/
    public static class TimerThread extends Thread{
        public final static long startTime = System.currentTimeMillis();
        @Override
        public void run() {
            try {
                while(true){
                    long t =  System.currentTimeMillis()-startTime;
                    System.out.println(t/1000+"."+t%1000);
                    Thread.sleep(100); //0.1s
                }

            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        //填充对象线程和打印线程同时启动
        FillListThread myThread = new FillListThread(); //造成GC，造成STW
        TimerThread timerThread = new TimerThread(); //时间打印线程
        myThread.start();
        timerThread.start();
    }

}
