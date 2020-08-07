package com.example.javaadvanced.serializable.jsonParse.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Gson将java对象转化为json默认是使用内置的TypeAdapter，
 * 如果默认的json转化策略无法满足特定的类型时，可以注册自定义的TypeAdapter。
 *
 * 示例中，注册了自定义的PointAdapter后，Gson把Point(5, 8)转为的json是"5,8"，而不是默认的{"x":5,"y":8}}
 *
 */
public class PointAdapterTest {

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static class PointAdapter extends TypeAdapter<Point> {

        @Override
        public Point read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {//进行非空判断
                reader.nextNull();
                return null;
            }
            String xy = reader.nextString();
            String[] parts = xy.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Point(x, y);
        }

        @Override
        public void write(JsonWriter writer, Point value) throws IOException {
            if (value == null) {//进行非空判断
                writer.nullValue();
                return;
            }
            String xy = value.getX() + "," + value.getY();
            writer.value(xy);
        }
    }

    public static void main(String[] args) {

        Point point = new Point(5,8);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Point.class, new PointAdapter())
                .create();
        String json = gson.toJson(point);
        System.out.println("json=" + json);

        //上面的TypeAdapter的read和write方法都进行非空判断，防止数据异常的情况。
        //如果不想在read和write方法中进行非空判断，则可以使用nullSafe()方法。
        //if PointAdapter didn't check for nulls in its read/write methods, you should instead use
       // builder.registerTypeAdapter(Point.class, new PointAdapter().nullSafe());

        gson = new GsonBuilder()
                .registerTypeAdapter(Point.class, new PointAdapter().nullSafe() )
                .create();
        json = gson.toJson(point);
        System.out.println("json=" + json);


    }

}
