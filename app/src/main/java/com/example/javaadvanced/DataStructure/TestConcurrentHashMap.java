package com.example.javaadvanced.DataStructure;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap(); //初始化ConcurrentHashMap
        //新增个人信息
        map.put("id", "1");
        map.put("name", "andy");
        map.put("sex", "男");
        //获取姓名
        String name = map.get("name");
        //Assert.assertEquals(name, "andy");
        System.out.println("name=" + name);
        //计算大小
        int size = map.size();
        System.out.println("size=" + size);
        //Assert.assertEquals(size, 3);
    }
}
