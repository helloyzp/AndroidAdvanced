package com.example.javaadvanced.jvm.ex1.jvmOptimization;

/**
 * @author King老师
 * <p>
 * 虚拟机优化技术
 * 栈的优化技术——栈帧之间数据的共享
 */
public class JVMStackFrame {

    public int work(int x) throws Exception {
        int z = (x + 5) * 10;//work方法的栈帧的局部变量表有10，与main方法的栈帧的操作数栈中的10是同一块内存
        Thread.sleep(Integer.MAX_VALUE);
        return z;
    }

    public static void main(String[] args) throws Exception {
        JVMStackFrame jvmStack = new JVMStackFrame();
        jvmStack.work(10);//10放入main方法的栈帧的操作数栈中
    }
}
