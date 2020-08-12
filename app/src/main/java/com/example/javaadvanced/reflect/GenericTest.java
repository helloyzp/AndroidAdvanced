package com.example.javaadvanced.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 通过子类继承泛型父类去获取泛型类型， 即通过调用子类的Class对象的getGenericSuperclass()方法获取直接父类的Type类型
 */
public class GenericTest {

    public static abstract class ParentClass<T> {
        T name;

        public T getName() {
            return name;
        }

        public void setName(T name) {
            this.name = name;
        }
    }

    public static class Child extends ParentClass<String> {

    }

    /**
     * 通过子类继承泛型父类去获取泛型类型
     */
    public static void getActualType() {
        Child child = new Child();
        Type type = child.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actualtype = parameterizedType.getActualTypeArguments()[0];
            System.out.println(actualtype);//class java.lang.String

        }
    }


    public static void main(String[] args) {
        getActualType();
    }

}
