package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * GenericDeclaration
 * getGenericDeclaration()方法获取声明了类型变量的实体
 */
public class GenericDeclarationTest {

    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Class clz = hashMap.getClass();
        TypeVariable[] typeVariables = clz.getTypeParameters();//getTypeParameters()获取类型变量K,V
        System.out.println("actualTypeArguments:" + Arrays.toString(typeVariables));
        for(TypeVariable typeVariable: typeVariables) {
            System.out.println("getGenericDeclaration(): " + typeVariable.getGenericDeclaration());
        }

    }
}
