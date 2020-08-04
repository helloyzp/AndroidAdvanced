package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 枚举、注解、数组、基本数据类型以及平常我们创建的类(不是泛型类)都是Class类型
 */
public class ClassTypeTest {

    private HashMap hashMap;
    private HashMap<String, Object> hashMap2;//不是Class类型，是ParameterizedType类型
    private int[] arr; //数组

    private int i;//基本类型
    private Integer integer;//基本类型的包装类型

    private double d;
    private Double aDouble;

    enum Fruit{
        APPLE, GRAPE
    }

    private Fruit apple = Fruit.APPLE;//枚举


    public @interface MyAnnotation{

    }
    private MyAnnotation annotation; //注解

    private Void voidParam;

    public static void main(String[] args) {

        Field[] fields = ClassTypeTest.class.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Type type = f.getGenericType();
            System.out.println("FieldName: " + f.getName() + " , is instanceof Class : " + (type instanceof Class) );

        }

    }

}
