package com.example.javaadvanced.network.okhttp;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

/**
 * 测试okhttp的代理实现
 */
public class OkHttpProxyTest {

    //SOCKS代理
    OkHttpClient socksClient =
            new OkHttpClient.Builder()
                    .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 808)))
                    //.retryOnConnectionFailure(false)//配置连接失败时不重试，默认是true
                    .build();

    //HTTP代理
    OkHttpClient httpClient =
            new OkHttpClient.Builder()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    "114.239.145.90", 808)))
                    .proxyAuthenticator(new Authenticator() {//如果配置了代理服务器，会返回407要求验证用户名和密码
                        @Nullable
                        @Override
                        public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
                            return response.request().newBuilder()
                                    .addHeader("Proxy-Authorization", Credentials.basic("username", "password"))
                                    .build();
                        }
                    })
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            //.....
                            Response proceed = chain.proceed(chain.request());
                            //......
                            return proceed;
                        }
                    })
                    .build();

    //OkHttp的proxy、proxySelector、dns的使用
    OkHttpClient httpClient2 =
            new OkHttpClient.Builder()
                    //设置单个代理
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("114.239.145.90", 808)))
                    //设置多个代理
                    .proxySelector(new ProxySelector() {
                        @Override
                        public List<Proxy> select(URI uri) {
                            return null;
                        }

                        @Override
                        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

                        }
                    })
                    //设置dns
                    .dns(new Dns() {
                        @NotNull
                        @Override
                        public List<InetAddress> lookup(@NotNull String s) throws UnknownHostException {
                            return null;
                        }
                    })
                    .build();


    public void testOkHttpHttpProxy() throws IOException {
        Request request = new Request.Builder()
                .url("http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key" +
                        "=13cb58f5884f9749287abbead9c658f2")
                .build();
        //执行同步请求
        Call call = httpClient.newCall(request);
        Response response = call.execute();

        //获得响应
        ResponseBody body = response.body();
        System.out.println("http代理 响应==》" + body.string());

    }


    public void testOkHttpSocksProxy() throws IOException {
        //启动 socks代理
        new Thread() {
            @Override
            public void run() {
                try {
                    SocksProxy.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Request request = new Request.Builder()
                .url("http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key" +
                        "=13cb58f5884f9749287abbead9c658f2")
                .build();
        //执行同步请求
        Call call = socksClient.newCall(request);
        Response response = call.execute();

        //获得响应
        ResponseBody body = response.body();
        System.out.println("socks代理 响应==》" + body.string());

    }

    public static void main(String[] args) throws IOException {
        OkHttpProxyTest okHttpProxyTest = new OkHttpProxyTest();
        okHttpProxyTest.testOkHttpHttpProxy();
        //okHttpProxyTest.testOkHttpSocksProxy();
    }


}