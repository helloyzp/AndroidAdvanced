package com.example.javaadvanced.jvm.AndroidVM;

/**
 * 测试用的有bug的类，修改bug即将最后一行代码注释掉
 */
public class Demo {
    public static void test() {
        int a = 1;
        int b = 2;
        int c = a + b;
        throw new UnsupportedOperationException("hahahaha");
    }
}
