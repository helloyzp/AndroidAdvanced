package com.example.javaadvanced.jvm.ex1.jvmOptimization;
/**
 * @author King老师
 *
 * 虚拟机优化技术
 * 编译优化技术——方法内联
 */
public class MethodInline {

    public static void main(String[] args) {
        //boolean result = max(1,2); //调用max方法：虚拟机栈 --入栈（max 栈帧）
        boolean result = 1>2;
    }
    public static boolean max(int a,int b){//方法的执行入栈帧。
        return a>b;
    }
}
