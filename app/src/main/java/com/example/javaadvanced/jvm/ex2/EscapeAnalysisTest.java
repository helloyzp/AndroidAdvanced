package com.example.javaadvanced.jvm.ex2;

/**
 * @author King老师
 * 逃逸分析-栈上分配
 *
 * 逃逸分析的作用就是分析对象的作用域是否会逃逸出方法之外，在server模式下才可以开启(JDK8默认开启)
 *
 * -XX:+PrintGC  打印GC信息
 * -XX:+DoEscapeAnalysis 开启逃逸分析， JDK8默认开启逃逸分析
 * -XX:-DoEscapeAnalysis 关闭逃逸分析
 */
public class EscapeAnalysisTest {

    private static Object globalObject = null;


    /**
     * 常见的逃逸场景1：方法返回值
     *
     *  aMethod()方法内部创建的对象的引用被返回给bMethod()方法内的变量obj，
     *  即bMethod()方法内的变量obj引用着aMethod()方法内部创建的对象，因此new Object()创建的对象发生了逃逸
     *
     * @return
     */
    public Object aMethod() {
        Object obj = new Object();
        return obj;
    }

    public void bMethod() {
        Object obj = aMethod();
    }

    /**
     * 常见的逃逸场景2： 给全局变量赋值， 全局逃逸
     *
     *
     *
     * cMethod()方法内部创建的对象被赋值给了全局变量globalObject，因此new Object()创建的对象发生了逃逸
     */
    public void cMethod() {
        globalObject = new Object();
    }

    /**
     * 常见的逃逸场景3： 引用传递， 参数逃逸
     *
     *
     *
     */
    public void dMethod(Object object) {
        Object obj = object;
        obj.toString();
    }

    public void eMethod() {
        Object obj = new Object();//new Object()创建的对象的作用域逃离了eMethod()方法
        dMethod(obj);
    }


    static class MyObject {
        int a;
        double b;

        MyObject(int a, double b) {
            this.a = a;
            this.b = b;
        }
    }

    /**
     * 没有逃逸
     *
     * 进行逃逸分析后，确定创建的MyObject对象不会逃逸出allocate()方法，所以new MyObject()进行栈上分配
     */
    static void allocate() {
        MyObject myObject = new MyObject(2020, 2020.6);
    }


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000000; i++) {//5千万的对象，为什么不会垃圾回收
            allocate();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
        Thread.sleep(600000);
    }


}
