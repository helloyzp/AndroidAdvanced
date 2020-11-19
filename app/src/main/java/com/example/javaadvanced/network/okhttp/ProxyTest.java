package com.example.javaadvanced.network.okhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ProxyTest {

    public static void main(String[] args) throws IOException {
        ProxyTest proxyTest = new ProxyTest();
        proxyTest.testHttpNoProxy();
        //proxyTest.testHttpProxy();
        //proxyTest.testHttpsProxy();
        //proxyTest.testSocksProxy();
    }

    /**
     * 普通http请求，没有使用代理
     *
     * @throws IOException
     */
    public void testHttpNoProxy() throws IOException {

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("restapi.amap.com", 808));//restapi.amap.com是需要请求的目标主机
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        StringBuilder sb = new StringBuilder();
        sb.append("GET /v3/weather/weatherInfo?city=长沙&key" +
                "=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
        sb.append("Host: restapi.amap.com\r\n\r\n");
        os.write(sb.toString().getBytes());
        os.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String msg;
        while ((msg = reader.readLine()) != null) {
            System.out.println(msg);
        }
    }


    /**
     * 使用http代理，发送Http请求
     *
     * @throws IOException
     */
    public void testHttpProxy() throws IOException {
        //okhttp的用法，还可以
        // new Socket(new Proxy(Type.HTTP,new InetSocketAddress("114.239.145.90", 808)))
        // connect(new InetSocketAddress("restapi.amap.com", 80))
        //然后直接 发送准确的http数据就可以了即： GET /v3/weather/weatherInfo... HTTP/1.1
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("114.239.145.90", 808));//114.239.145.90是代理服务器，代理服务器就是转发的作用
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        /**注意使用 http普通代理 ，发送的请求行中需要加上域名：
         *    sb.append("GET http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key" +
         *                 "=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
         *    sb.append("Host: restapi.amap.com\r\n\r\n");
         *
         * 而如果没有使用代理，发送的http请求报文是这样的：
         *    sb.append("GET /v3/weather/weatherInfo?city=长沙&key" +
         *                 "=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
         *    sb.append("Host: restapi.amap.com\r\n\r\n");
         *    请求行中没有域名，域名是加在Host请求头中的。
         *
         */
        StringBuilder sb = new StringBuilder();
        sb.append("GET http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key" +
                "=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
        sb.append("Host: restapi.amap.com\r\n\r\n");
        os.write(sb.toString().getBytes());
        os.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String msg;
        while ((msg = reader.readLine()) != null) {
            System.out.println(msg);
        }
    }

    /**
     * 使用http代理，发送Https请求
     *
     * @throws IOException
     */
    public void testHttpsProxy() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("114.239.145.90", 808));
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        //1.先发送CONNECT请求，与代理服务器完成代理协议连接
        StringBuilder sb = new StringBuilder();
        sb.append("CONNECT restapi.amap.com " +
                "HTTP/1.1\r\n");
        sb.append("Host: restapi.amap.com\r\n\r\n");

        os.write(sb.toString().getBytes());
        os.flush();

        //读取代理服务器返回的结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String msg;
        while ((msg = reader.readLine()) != null) {
            if (msg.isEmpty()) {
                break;
            }
            System.out.println(msg);//代理服务器返回的结果
        }

        //2.成功后再使用ssl包装与代理服务的socket，然后发送GET请求
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(
                socket, "restapi.amap.com", 443, true);

        OutputStream outputStream = sslSocket.getOutputStream();
        InputStream inputStream = sslSocket.getInputStream();

        //这个请求会被代理转发给connect协商的目标服务器
        StringBuilder request = new StringBuilder();
        request.append("GET /v3/weather/weatherInfo?city=长沙&key=13cb58f5884f9749287abbead9c658f2 " +
                "HTTP/1.1\r\n");
        request.append("Host: restapi.amap.com\r\n");
        request.append("\r\n");

        outputStream.write(request.toString().getBytes());
        outputStream.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((msg = br.readLine()) != null) {
            System.out.println(msg);
        }
    }

    /**
     * 使用SOCKS代理
     * @throws IOException
     */
    public void testSocksProxy() throws IOException {
        //启动本地的SOCKS代理服务器
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


        Socket socket = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 808)));
        //okhttp 设置了socks代理就传递不解析的域名，让代理服务器解析
        socket.connect(InetSocketAddress.createUnresolved("restapi.amap.com", 80));


        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        StringBuilder sb = new StringBuilder();
        sb.append("GET /v3/weather/weatherInfo?city=长沙&key" +
                "=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
        sb.append("Host: restapi.amap.com\r\n");
        sb.append("\r\n");
        os.write(sb.toString().getBytes());
        os.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String msg;
        while ((msg = reader.readLine()) != null) {
            System.out.println(msg);
        }
    }

}
