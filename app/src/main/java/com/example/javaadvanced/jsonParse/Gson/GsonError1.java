package com.example.javaadvanced.jsonParse.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Gson解析常见的错误:
 * Expected BEGIN_ARRAY but was STRING at line 1 column 27
 * <p>
 * 这种错误一般都是原来是一个字段需要是数组类型，但是事实上给的是""导致的。
 * <p>
 * 正常数据：
 * {
 * 'name':    'java',
 * 'authors':  [{'id':1','name':Joshua Bloch'}, {'id':2','name':'Tom'}]
 * }
 * <p>
 * 异常数据：
 * {
 * 'name':    'java',
 * 'authors':  ''
 * }
 * <p>
 * <p>
 * 解决办法:
 * 1.让字段的数据返回null即可解决问题
 * 2.用Gson自带的解决方案
 */
public class GsonError1 {

    /**
     * 书本类
     **/
    public static class Book {
        private String name;
        private Author[] authors;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Author[] getAuthors() {
            return authors;
        }

        public void setAuthors(Author[] authors) {
            this.authors = authors;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", authors=" + Arrays.toString(authors) +
                    '}';
        }
    }


    /***
     *书本作者类
     **/
    public static class Author {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }



    public static class BookDeserializer implements JsonDeserializer<Book> {

        @Override
        public Book deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            final JsonElement jsonTitle = jsonObject.get("name");
            final String name = jsonTitle.getAsString();

            JsonElement jsonAuthors = jsonObject.get("authors");

            final Book book = new Book();
            if (jsonAuthors.isJsonArray()) {//如果数组类型，此种情况是我们需要的
                //关于context在文章最后有简单说明
                Author[] authors = context.deserialize(jsonAuthors, Author[].class);
                book.setAuthors(authors);
            } else {//此种情况为无效情况
                book.setAuthors(null);
            }

            book.setName(name);
            return book;
        }
    }


    public static class AuthorDeserializer implements JsonDeserializer {

        @Override
        public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            final Author author = new Author();
            author.setId(jsonObject.get("id").getAsInt());
            author.setName(jsonObject.get("name").getAsString());
            return author;
        }
    }


    //正常的数据情况，authors字段传入的是json数组
    public static void test1() {
        //TODO:

        String json = "{\n" +
                "    \"name\": \"java\",\n" +
                "    \"authors\": [\n" +
                "        {\n" +
                "            \"id\": \"1'\",\n" +
                "            \"name\": \"Joshua Bloch'\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"2'\",\n" +
                "            \"name\": \"Tom\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Gson gson = new Gson();
        Book book = gson.fromJson(json, Book.class);
        System.out.println(book);

    }

    //异常的数据情况，authors字段传入的是空字符串
    public static void test2() {
        //TODO:
        String json = "{\n" +
                "    \"name\": \"java\",\n" +
                "    \"authors\": \"\"\n" +
                "}";
        Gson gson = new Gson();
        Book book = gson.fromJson(json, Book.class);
        System.out.println(book);
    }

    //通过注册自定义注册TypeAdapter解决上面的authors字段可能传入异常数据的情况
    public static void test3() {
        //TODO:
        String json = "{\n" +
                "    \"name\": \"java\",\n" +
                "    \"authors\": \"\"\n" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();

        //注册TypeAdapter
        gsonBuilder.registerTypeAdapter(Book.class, new BookDeserializer());
        gsonBuilder.registerTypeAdapter(Author.class, new AuthorDeserializer());

        Gson gson = gsonBuilder.create();
        Book book = gson.fromJson(json, Book.class);
        System.out.println(book);
    }


    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
    }


}
