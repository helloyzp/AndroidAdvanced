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
    //array是静态变量，存储在方法区，new int[10]创建了一个数组对象，存储在堆中
    public static int[] array = new int[10];

    public static void main(String[] args) {
        int[] arr = new int[10];//arr引用变量存储在main方法的栈帧的局部变量表中，new int[10]创建了一个数组对象，存储在堆中
        if(arr instanceof Object) {
            System.out.println("数组是对象");
        }
    }


}
