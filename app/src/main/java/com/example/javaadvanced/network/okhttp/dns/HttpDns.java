package com.example.javaadvanced.network.okhttp.dns;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class HttpDns implements Dns {
    private String TAG = "HttpDns";

    private static final Dns SYSTEM = Dns.SYSTEM;

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Log.e(TAG, "lookup(), hostname=" + hostname);
/*        //根据HttpDns服务商提供的SDK实现HttpDns

        String ip = DNSHelper.getIpByHost(hostname);
        if (ip != null && !ip.equals("")) {
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            Log.e("HttpDns", "inetAddresses:" + inetAddresses);
            return inetAddresses;
        }*/
        return SYSTEM.lookup(hostname);
    }
}