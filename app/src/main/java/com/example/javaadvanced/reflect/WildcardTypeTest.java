package com.example.javaadvanced.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * WildcardType represents a wildcard type expression, such as ?, ? extends Number, or ? super Integer.
 */
public class WildcardTypeTest {
    private List<? extends Number> numbers;
    private List<? super Integer> integers;

    public static void main(String[] args) {

        Field[] fields  = WildcardTypeTest.class.getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            Type type = field.getGenericType();
            if(type instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
                //输出其是否为通配符类型
                System.out.println( actualTypeArgument + " is instanceof WildcardType : " + (actualTypeArgument instanceof WildcardType));
                if (actualTypeArgument instanceof WildcardType) {
                    int lowIndex = ((WildcardType) actualTypeArgument).getLowerBounds().length - 1;
                    int upperIndex = ((WildcardType) actualTypeArgument).getUpperBounds().length - 1;
                    //输出上边界与下边界
                    System.out.println("getLowerBounds(): " + (lowIndex >= 0 ? ((WildcardType) actualTypeArgument).getLowerBounds()[lowIndex] : "null")
                            + "; getUpperBounds(): " + (upperIndex >= 0 ? ((WildcardType) actualTypeArgument).getUpperBounds()[upperIndex] : "Object"));
                }
            }
        }

    }

}
