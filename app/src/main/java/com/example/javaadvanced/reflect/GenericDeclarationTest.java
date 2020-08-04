package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;

public class GenericDeclarationTest {

    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Class clz = hashMap.getClass();
        Type type = clz.getGenericSuperclass();//获取直接父类的类型Type
        System.out.println("getGenericSuperclass()=" + type);
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.println("actualTypeArguments:" + Arrays.toString(actualTypeArguments));
        }

        System.out.println("getSuperclass()=" + clz.getSuperclass());


        Type[] types = clz.getGenericInterfaces();
        System.out.println("getGenericInterfaces()=" + Arrays.toString(types));

        TypeVariable[] typeVariables = clz.getTypeParameters();//getTypeParameters()获取类型变量K,V
        System.out.println("actualTypeArguments:" + Arrays.toString(typeVariables));

    }
}
