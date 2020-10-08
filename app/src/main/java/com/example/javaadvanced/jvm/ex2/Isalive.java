package com.example.javaadvanced.jvm.ex2;

/**
 * @author King老师
 * VM Args：-XX:+PrintGC
 *
 * 相互引用，判断对象存活
 */
public class Isalive {
    public Object instance = null;
    //字节数组用于占据内存，便于判断分析GC
    private byte[] bigSize = new byte[10 * 1024 * 1024];//一个10M的字节数组

    public static void main(String[] args) {//main()方法执行时对应一个栈帧
        //objectA和objectB是引用变量，存储在栈帧中的局部变量表
        //main()方法执行完成前，objectA和objectB属于GC Roots，
        //main()方法执行完成后，objectA和objectB跟随栈帧被销毁，不再引用之前指向的对象
        Isalive objectA = new Isalive();
        Isalive objectB = new Isalive();
        //相互引用
        objectA.instance = objectB;
        objectB.instance = objectA;
        //切断可达
        objectA = null;
        objectB = null;
        //强制垃圾回收
        System.gc();
        /**
         * 输出GC log如下：
         * [GC (System.gc())  25682K->928K(249344K), 0.0008877 secs]
         * [Full GC (System.gc())  928K->782K(249344K), 0.0044975 secs]
         *
         * 根据GC log可以知道jvm使用的不是引用计数算法，而是可达性分析算法。
         */
    }
}
