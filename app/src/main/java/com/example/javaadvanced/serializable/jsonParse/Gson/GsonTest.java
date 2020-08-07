package com.example.javaadvanced.serializable.jsonParse.Gson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;

public class GsonTest {

    static class GsonBean {
        public int i;
        public String str;
    }

    //基本使用
    public static void main(String... args) {
        Gson gson = new Gson();

        //整数转json
        System.out.println(gson.toJson(1));
        //字符串转json
        System.out.println(gson.toJson("zero"));
        //数组转json
        int[] values = {1, 2, 3};
        System.out.println(gson.toJson(values));


        //json整数值的解析
        int i = gson.fromJson("1", int.class);
        System.out.println("i: " + i);

        //json字符串类型的解析
        String str = gson.fromJson("str", String.class);
        System.out.println("str=" + str);

        //json对象的解析
        GsonBean gsonBean = new GsonBean();
        gsonBean.i = 2;
        gsonBean.str = "str";

        String json = gson.toJson(gsonBean);
        System.out.println("json: " + json);


        GsonBean gsonBean1 = gson.fromJson(json, GsonBean.class);
        System.out.println("gsonBean1: " + gsonBean1);



    }
}
