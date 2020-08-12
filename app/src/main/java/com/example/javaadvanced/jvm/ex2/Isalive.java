package com.example.javaadvanced.jvm.ex2;
/**
 * @author  King老师
 * VM Args：-XX:+PrintGC
 * 判断对象存活
 */
public class Isalive {
    public Object instance =null;
    //占据内存，便于判断分析GC
    private byte[] bigSize = new byte[10*1024*1024];

    public static void main(String[] args) {
        Isalive objectA = new Isalive();
        Isalive objectB = new Isalive();
        //相互引用
        objectA.instance = objectB;
        objectB.instance = objectA;
        //切断可达
        objectA =null;
        objectB =null;
        //强制垃圾回收
        System.gc();
    }
}
