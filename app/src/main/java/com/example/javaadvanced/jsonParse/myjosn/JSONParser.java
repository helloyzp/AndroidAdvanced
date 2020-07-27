package com.example.javaadvanced.jsonParse.myjosn;

import com.example.javaadvanced.jsonParse.myjosn.parser.Parser;
import com.example.javaadvanced.jsonParse.myjosn.tokenizer.CharReader;
import com.example.javaadvanced.jsonParse.myjosn.tokenizer.TokenList;
import com.example.javaadvanced.jsonParse.myjosn.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.StringReader;


public class JSONParser {

    private Tokenizer tokenizer = new Tokenizer();//词法分析

    private Parser parser = new Parser();//语法解析

    public Object fromJSON(String json) throws IOException {
        CharReader charReader = new CharReader(new StringReader(json));
        TokenList tokens = tokenizer.tokenize(charReader);
        return parser.parse(tokens);
    }

    public static void main(String[] args) throws IOException {
        String json = "{\n" +
                "    \"key\" : \"value\"\n" +
                "}";

        JSONParser jsonParser = new JSONParser();
        Object jsonObject = jsonParser.fromJSON(json);
        System.out.println("jsonObject=" + jsonObject);
    }
}
