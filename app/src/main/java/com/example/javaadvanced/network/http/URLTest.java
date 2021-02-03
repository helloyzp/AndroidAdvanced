package com.example.javaadvanced.network.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class URLTest {

     public static void main(String ... args) throws IOException {
         //http://www.baidu.com/index.html?key=abc&data=def#123

         //创建一个URL实例
         URL baidu = new URL("https://www.baidu.com");

         //?后面表示参数，#表示锚点
         URL url = new URL(baidu,"/index?wd=jdk文档#123");

         System.out.println("协议: " + url.getProtocol());
         System.out.println("主机: " + url.getHost());//url如果没写端口号，getHost()默认获取的是-1
         //如果未指定端口号，则使用默认的端口号，此时getPort返回-1
         System.out.println("端口: " + url.getPort());
         System.out.println("文件路径:" + url.getPath());
         System.out.println("查询字符串: " + url.getQuery());
         System.out.println("锚: " + url.getRef());

         //通过URL的openStream()方法获取URL实例所表示的资源的字节输入流
         InputStream inputStream = url.openStream();
         //字节流转字符流
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
         //给字符流添加缓冲
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         String data = null;
         while((data=bufferedReader.readLine()) != null) {
             System.out.print(data);
         }
         bufferedReader.close();
         inputStreamReader.close();
         inputStream.close();
     }

}
