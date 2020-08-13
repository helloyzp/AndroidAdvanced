package com.example.javaadvanced.jvm.ex1;

/**
 * 分析各个变量在JVM中的内存存储区域
 */
public class MemoryArea {

    //obj1是静态变量，存储在方法区，new Object()创建出的对象存储在堆中
    public static Object obj1 = new Object();
    //obj2是常量，存储在方法区，new Object()创建出的对象存储在堆中
    public static final Object obj2 = new Object();
    //str是静态变量，存储在方法区，"abc"是字面量，存储在方法区的运行时常量池中。
    public static String str = "abc";


}
