package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * ParameterizedType represents a parameterized type such as Collection<String>.
 */
public class ParameterizedTypeTest<T> {

    //是ParameterizedType，即参数化类型的变量
    private Collection<T> collection;
    private HashMap<String, Object> map;
    private HashMap<String, List<String>> map2;
    private Map.Entry<String, Object> entry;
    private HashSet<String> set;
    private Class<?> clz;

    //测试getActualTypeArguments()获取类型参数
    private List<ArrayList> a1;//返回ArrayList，Class类型
    private List<ArrayList<String>> a2;//返回ArrayList<String>，ParameterizedType类型
    private List<T> a3;//返回T，TypeVariable类型
    private List<? extends Number> a4; //返回? extends Number，WildcardType类型
    private List<ArrayList<String>[]> a5;//返回ArrayList<String>[]，GenericArrayType 类型


    //不是ParameterizedType，是普通类型的变量
    private Integer i;
    private String str;

    private static void printParameterizedType() {
        Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Type type = f.getGenericType();

            //打印是否是ParameterizedType类型
            System.out.println("FieldName: " + f.getName() + " , is instanceof ParameterizedType : " +
                    (type instanceof ParameterizedType));

            if (type instanceof ParameterizedType) {
                System.out.print("getActualTypeArguments:");
                for (Type param : ((ParameterizedType) type).getActualTypeArguments()) {
                    //打印实际类型参数
                    System.out.print(param);
                    if(param instanceof Class) {
                        System.out.print(" ，是Class类型");
                    }
                    if (param instanceof ParameterizedType) {
                        System.out.print(" ，是ParameterizedType类型");
                    }
                    if (param instanceof GenericArrayType) {
                        System.out.print(" ，是GenericArrayType类型");
                    }
                    if (param instanceof TypeVariable) {
                        System.out.print(" ，是TypeVariable类型");
                    }
                    if (param instanceof WildcardType) {
                        System.out.print(" ，是WildcardType类型");
                    }
                    System.out.print(", ");
                }
                System.out.print("\n");
                //打印所在的父类的类型
                System.out.println("getOwnerType:" + ((ParameterizedType) type).getOwnerType());
                //打印原始类型
                System.out.println("getRawType:" + ((ParameterizedType) type).getRawType());
            }
            System.out.println("---------------------------------");
        }
    }
    public static void main(String[] args) {
        printParameterizedType();
    }

}
