package com.example.javaadvanced.serializable.jsonParse.Gson;

import com.example.javaadvanced.reflect.GenericDeclarationTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeTokenTest {

    //待解析的json字符串： {"data":"data from server"}
    public class Response<T>{
        public T data;//简化数据, 省略了其他字段

        @Override
        public String toString() {
            return "Response{" +
                    "data=" + data +
                    '}';
        }
    }

    private Response<String> data;

    public static void main(String[] args) {
        String json = "{\"data\":\"data from server\"}";

        //fromJson(String json, Class<T> classOfT)的第二个参数classOfT期望获取Response<String>这种类型
        //Class responseClass = Response<String>.class; /但是这么写不能通过编译，因为Response<String>不是一个Class类型
        Class responseClass = Response.class; //只能这样获取类型, 但无法知道Response里面数据的类型

        Response<String> result = (Response<String>) new Gson().fromJson(json, responseClass);
        System.out.println("result=" + result);

        //Gson的解决方案
        Type type = new TypeToken<Response<String>>(){}.getType();
        System.out.println("type=" + type);//TypeTokenTest$Response<java.lang.String>
        result = new Gson().fromJson(json, type);
        System.out.println("result=" + result);

    }
}
