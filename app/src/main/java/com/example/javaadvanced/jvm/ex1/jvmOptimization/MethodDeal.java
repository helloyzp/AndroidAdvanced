package com.example.javaadvanced.jvm.ex1.jvmOptimization;
/**
 * @author King老师
 *
 * 虚拟机优化技术
 * 编译优化技术——方法内联
 */
public class MethodDeal {

    public static void main(String[] args) {
       // max(1,2);//调用max方法：  虚拟机栈 --入栈（max 栈帧）
        boolean i1 = 1>2;
    }
    public static boolean max(int a,int b){//方法的执行入栈帧。
        return a>b;
    }
}
