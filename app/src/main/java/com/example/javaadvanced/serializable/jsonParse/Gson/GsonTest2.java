package com.example.javaadvanced.serializable.jsonParse.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GsonTest2 {

    public static final String NAME = "name";
    public static final String PRICE = "price";

    static class Food {

        @SerializedName(value = "myName1", alternate = "myName2")
        private String name;
        private double price;

        public Food() {
            this(null, 5.0);
        }

        public Food(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    public static void main(String... args) {
        test1();
    }

    //使用方式1：如果没有注册自定义TypeAdapter，则使用默认的反射的TypeAdapter(ReflectiveTypeAdapterFactory.Adapter)，比较耗性能
    public static void test1() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls().create();
        Food food = new Food();
        String json = gson.toJson(food);
        System.out.println(json);
    }

    //使用方式2： 使用自定义TypeAdapter
    public static void test2() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Food.class
                , new TypeAdapter<Food>() {

                    @Override
                    public Food read(JsonReader in) throws IOException {
                        if (in.peek() == JsonToken.NULL) {//进行非空判断
                            in.nextNull();
                            return null;
                        }
                        //读取json串并封装成Food对象返回之
                        final Food food = new Food();
                        in.beginObject();
                        while (in.hasNext()) {
                            switch (in.nextName()) {
                                case NAME:
                                    food.name = in.nextString();
                                    break;
                                case PRICE:
                                    food.price = in.nextInt();
                                    break;
                            }
                        }
                        in.endObject();
                        return food;
                    }

                    @Override
                    public void write(JsonWriter out, Food value) throws IOException {
                        if (value == null) {//进行非空判断
                            out.nullValue();
                            return;
                        }
                        //把Food对象制定成你自己定义的格式的字符串进行输出，不一定是json格式了，就看你怎么组织
                        out.beginObject();
                        out.name(NAME).value(value.name);
                        out.name(PRICE).value(value.price);
                        out.endObject();
                    }


                }).create();

        Food food = new Food();
        String json = gson.toJson(food);
        System.out.println(json);

    }

    //nullSafe()使用，使用了nullSafe()方法后，上述TypeAdapter的写法
    public static void test3() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Food.class
                , new TypeAdapter<Food>() {

                    @Override
                    public Food read(JsonReader in) throws IOException {
                        //后面会调用TypeAdapter的nullSafe()方法，所以read和write方法都不用进行null的判断了

                        //读取json串并封装成Food对象返回之
                        final Food food = new Food();
                        in.beginObject();
                        while (in.hasNext()) {
                            switch (in.nextName()) {
                                case NAME:
                                    food.name = in.nextString();
                                    break;
                                case PRICE:
                                    food.price = in.nextInt();
                                    break;
                            }
                        }
                        in.endObject();
                        return food;
                    }

                    @Override
                    public void write(JsonWriter out, Food value) throws IOException {
                        //后面会调用TypeAdapter的nullSafe()方法，所以read和write方法都不用进行null的判断了

                        //把Food对象制定成你自己定义的格式的字符串进行输出，不一定是json格式了，就看你怎么组织
                        out.beginObject();
                        out.name(NAME).value(value.name);
                        out.name(PRICE).value(value.price);
                        out.endObject();
                    }

                }.nullSafe()).create();
        Food food = new Food();
        String json = gson.toJson(food);
        System.out.println(json);

    }

}
