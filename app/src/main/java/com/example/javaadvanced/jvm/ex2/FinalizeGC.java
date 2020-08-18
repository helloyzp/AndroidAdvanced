package com.example.javaadvanced.jvm.ex2;

/**
 * @author King老师
 * 对象的自我拯救
 */
public class FinalizeGC {
    public static FinalizeGC instance = null;

    public void isAlive() {
        System.out.println("I am still alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize() method executed");
        FinalizeGC.instance = this; //在finalize()方法中重新建立引用
    }

    public static void main(String[] args) throws Throwable {
        instance = new FinalizeGC();
        //对象进行第1次GC
        instance = null;
        System.gc();
        Thread.sleep(1000);//finalize()方法所在的线程优先级很低，所以main线程需要等待
        if (instance != null) {
            instance.isAlive(); //第一次自救成功
        } else {
            System.out.println("I am dead！");
        }
        //对象进行第2次GC
        instance = null;
        System.gc();
        Thread.sleep(1000);
        if (instance != null) {
            instance.isAlive();
        } else {
            //finalize()只执行一次，所以第一次自救成功，第二次自救失败
            System.out.println("I am dead！"); //第二次自救失败
        }
    }
}
