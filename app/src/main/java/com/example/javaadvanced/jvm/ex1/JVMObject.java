package com.example.javaadvanced.jvm.ex1;

/**
 * @author King老师
 * 从底层深入理解运行时数据区
 * -Xms30m -Xmx30m -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 * -Xss1m
 *
 *  查看对象在新生代和老年代的地址
 *  查看栈帧
 */
public class JVMObject {
    public final static String MAN_TYPE = "man"; // 常量
    public static String WOMAN_TYPE = "woman";  // 静态变量

    public static void  main(String[] args)throws Exception {//栈帧
        Teacher T1 = new Teacher();//Teacher对象存在堆中，  T1引用变量，存在虚拟机栈的栈帧的局部变量表中
        T1.setName("Mark");
        T1.setSexType(MAN_TYPE);
        T1.setAge(36);
        for (int i=0;i<15;i++){//进行15次垃圾回收
            System.gc();//垃圾回收
        }
        Teacher T2 = new Teacher();
        T2.setName("King");
        T2.setSexType(MAN_TYPE);
        T2.setAge(18);
        Thread.sleep(Integer.MAX_VALUE);//线程休眠很久很久
    }
}

class Teacher{
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
