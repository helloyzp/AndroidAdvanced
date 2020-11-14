package com.example.javaadvanced.network.okhttp;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpTest {
    OkHttpClient client = new OkHttpClient();


    public void testOkHttp() throws IOException {
//        get("http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key" +
//                "=13cb58f5884f9749287abbead9c658f2");
//
//        post("http://restapi.amap.com/v3/weather/weatherInfo");

        try {
            URI uri = new URI("http://restapi.amap.com");
            List<Proxy> proxyList = ProxySelector.getDefault().select(uri);
            System.out.println(proxyList.get(0).address());
            System.out.println(proxyList.get(0).type());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    void get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //执行同步请求
        Call call = client.newCall(request);
        Response response = call.execute();

        //获得响应
        ResponseBody body = response.body();
        System.out.println(body.string());
    }

    //application/x-www-form-urlencoded"
    void post(String url) throws IOException {
        RequestBody requestBody = new FormBody.Builder().add("city", "长沙").add("key",
                "13cb58f5884f9749287abbead9c658f2").build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();

        //执行同步请求
        Call call = client.newCall(request);
        Response response = call.execute();

        //获得响应
        ResponseBody body = response.body();
        System.out.println(body.string());
    }

    public static void main(String[] args) throws IOException {
        OkhttpTest okhttpTest  = new OkhttpTest();
        okhttpTest.testOkHttp();

    }
}
