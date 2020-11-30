package com.example.javaadvanced.network.okhttp.dns;

import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.OkHttpClient;

public class OkHttpDnsTest {

    OkHttpClient socksClient =
            new OkHttpClient.Builder()
                    .dns(new HttpDns()) //配置DNS
                    .build();

    public static void main(String[] args) {

    }
}
