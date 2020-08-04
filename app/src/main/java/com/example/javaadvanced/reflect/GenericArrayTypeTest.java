package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * GenericArrayType represents an array type whose component type is either a parameterized type or a type variable.
 * @param <T>
 */
public class GenericArrayTypeTest<T> {

    //泛型数组类型
    private T[] value;
    private T[][] value2;
    private List<String>[] list;
    private List<T>[] list2;

    //不是泛型数组类型
    private List<String> singleList;
    private T singleValue;

    public static void main(String[] args){
        Field[] fields = GenericArrayTypeTest.class.getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
            //输出当前变量是否为GenericArrayType类型
            System.out.print("Field: " + field.getName() + " is instanceof GenericArrayType : " + (field.getGenericType() instanceof GenericArrayType));
            if (field.getGenericType() instanceof GenericArrayType){
                //如果是GenericArrayType，则输出当前泛型类型
                System.out.print("; getGenericComponentType(): " + (((GenericArrayType)field.getGenericType()).getGenericComponentType()));
            }
            System.out.print("\n");
        }
    }

}
