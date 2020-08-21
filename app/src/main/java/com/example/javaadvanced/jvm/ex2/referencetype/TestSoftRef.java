package com.example.javaadvanced.jvm.ex2.referencetype;

import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @author King老师
 * 软引用
 * -Xms20m -Xmx20m
 *
 * 一些有用但是并非必需，用软引用关联的对象，系统将要发生内存溢出（OutOfMemory）之前，
 * 这些对象就会被回收（如果这次回收后还是没有足够的空间，才会抛出内存溢出异常）。
 */

public class TestSoftRef {
    //对象
    public static class User {
        public int id = 0;
        public String name = "";

        public User(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }

    }

    //
    public static void main(String[] args) {
        User user = new User(1, "King"); //new是强引用
        SoftReference<User> userSoft = new SoftReference<User>(user);//软引用
        user = null;//干掉强引用，确保这个实例只有userSoft软引用指向它
        System.out.println(userSoft.get()); //看一下这个对象是否还在
        System.gc();//进行一次GC垃圾回收  千万不要写在业务代码中（写在业务代码中会导致应用卡顿）。
        System.out.println("After gc");
        System.out.println("userSoft.get()=" + userSoft.get());
        //往堆中填充数据，导致OOM
        List<byte[]> list = new LinkedList<>();
        try {
            for (int i = 0; i < 100; i++) {
                //System.out.println("*************"+userSoft.get());
                list.add(new byte[1024 * 1024 * 1]); //1M的对象
            }
        } catch (Throwable e) {
            //抛出了OOM异常时打印软引用对象
            System.out.println("Exception*************" + e.getMessage() );
			System.out.println("userSoft.get()=" + userSoft.get());//发生OOM时软引用指向的对象被回收
        }

    }
}
