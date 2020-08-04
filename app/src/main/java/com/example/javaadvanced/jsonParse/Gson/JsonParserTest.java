package com.example.javaadvanced.jsonParse.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

/**
 * JsonReader主要作用就是读取json字符串并转化为一个token流，而JsonParser就是根据这个token流进行json语法分析并转为java对象。
 */
public class JsonParserTest {

    public static void main(String[] args) {
        String json = "{ \"name\":\"java书籍\", \"authors\":[\"Jerry\",\"Tom\"]}";
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        System.out.println("jsonElement=" + jsonElement);
        System.out.println(jsonElement.getAsJsonObject().get("authors").isJsonArray());
    }

}
