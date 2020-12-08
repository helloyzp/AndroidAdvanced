package com.example.memorydemo.heap;

/**
 * Shallow Heap Vs Retained Heap
 */



public class MATHeap {

    public static class A {
        private static byte[] b = new byte[10*1000];
        private B b1 = new B();
        private C c1 = new C();
    }
    static class B {
        private D d1 = new D();
        private E e1 = new E();
    }
    static  class C {
        private F f1 = new F();
        private G g1 = new G();
    }
    static class D {
    }
    static  class E {
    }
    static  class F {
    }
    static  class G {
    }

    public static void main(String[] args) throws Exception {
        A a = new A();
        Thread.sleep(Integer.MAX_VALUE);//线程休眠
    }
}
