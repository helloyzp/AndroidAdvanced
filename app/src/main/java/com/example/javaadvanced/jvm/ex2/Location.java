package com.example.javaadvanced.jvm.ex2;

public class Location {

    private String city;
    private String region;


    public static void testStringCreate() {

        Location location = new Location();
        location.setCity("深圳");//"深圳"和"南山"两个字符串在常量池中创建
        location.setRegion("南山");

        //JVM首先会检查该对象是否在字符串常量池中，如果在，就返回该对象引用，否则新的字符串将在常量池中被创建。
        //这种方式可以减少同一个值的字符串对象的重复创建，节约内存。
        String str = "abc";


        //首先在编译类文件时，"abcd"常量字符串将会放入到常量结构中，在类加载时，“abcd"将会在常量池中创建；
        //其次，在调用 new 时，JVM 命令将会调用 String 的构造函数，同时引用常量池中的"abcd” 字符串，
        // 在堆内存中创建一个 String 对象；最后，str 将引用 String 对象。
        String str1 = new String("abcd");


    }

    /**
     * String的intern()方法
     */
    public static void testStringIntern() {
        //new Sting() 会在堆内存中创建一个a的String对象，
        // “king"将会在常量池中创建
        // 在调用intern方法之后，会去常量池中查找是否有等于该字符串对象的字符串(通过equals(Object)判断是否相等)，
        // 有就返回常量池中该字符串对象的引用。 否则就将该字符串"king"加入常量池中，并返回该字符串的引用4
        String a = new String("king").intern();

        //调用 new Sting() 会在堆内存中创建一个b的String 对象，。
        // 在调用intern方法之后，会去常量池中查找是否有等于该字符串对象的字符串(通过equals(Object)判断是否相等)，
        // 有就返回常量池中该字符串对象的引用。 否则就将该字符串"king"加入常量池中，并返回该字符串的引用
        String b = new String("king").intern();
        //所以 a 和 b 引用的是同一个对象, 都是指string pool中的"king"
        if (a == b) {
            System.out.println("a==b");
        } else {
            System.out.println("a!=b");
        }

    }


    /**
     * 字符串拼接
     *
     * String的+操作的实现原理
     */
    public static void testStringConcat() {
        String hello = "hello";
        String hel = "hel";
        String lo = "lo";

//		+ 两边都是常量，编译时会在class常量池中放置拼接后的字符串"hello"，类加载后放入运行时常量池，
//		hello1指向的是运行时常量池中的"hello"字符串对象。
        String hello1 = "hel" + "lo";
        System.out.println(hello == hello1); //true

//		+ 两边有一个不是常量
        String hello2 = "hel" + lo;
        System.out.println(hello == hello2);//false

        String hello3 = hel + lo;
        System.out.println(hello == hello3);//false
        System.out.println(hello2 == hello3);//false

        //常量池中只有"appleorange"字符串对象，没有"apple"字符串对象，也没有"orange"字符串对象
        String str = "apple" + "orange";
    }


    public static void main(String[] args) {

        testStringCreate();
        testStringConcat();
        testStringIntern();

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
