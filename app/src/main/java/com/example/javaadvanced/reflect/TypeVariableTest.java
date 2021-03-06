package com.example.javaadvanced.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * TypeVariable is the common superinterface for type variables of kinds.
 * @param <K>
 * @param <T>
 */
public class TypeVariableTest<K extends Number, T> {


    private K key;  //K指定了上边界Number
    private T value;  //T没有指定上边界，其默认上边界为Object

    public static void main(String[] args){
        //getTypeParameters()获取TypeVariableTest上声明的类型变量K,T
        Type[] types = TypeVariableTest.class.getTypeParameters();
        for (Type type : types){
            TypeVariable t = (TypeVariable) type;
            //输出名称
            System.out.println("getName(): " + t.getName());
            int index = t.getBounds().length - 1;
            //输出上边界
            System.out.println("getBounds(): " + t.getBounds()[index]);
            //输出类型变量所在的类的类型
            System.out.println("getGenericDeclaration(): " + t.getGenericDeclaration());

        }


        HashMap<String, Object> hashMap = new HashMap<>();
        Class clz = hashMap.getClass();
        TypeVariable[] typeVariables = clz.getTypeParameters();//getTypeParameters()获取类型变量K,V
        System.out.println("actualTypeArguments:" + Arrays.toString(typeVariables));
    }

}
