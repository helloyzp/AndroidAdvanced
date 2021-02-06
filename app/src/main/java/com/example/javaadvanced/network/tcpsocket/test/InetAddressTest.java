package com.example.javaadvanced.network.tcpsocket.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * InetAddress的简单实用
 */
public class InetAddressTest {

     public static void main(String ... args) throws UnknownHostException {

         InetAddress address = InetAddress.getLocalHost();
         System.out.println("计算机: " + address.getHostName());
         System.out.println("IP地址: " + address.getHostAddress());

         byte[] bytes = address.getAddress(); //获取字节数组形式的IP地址
         System.out.println("bytes: " + Arrays.toString(bytes));
         System.out.println(address);

         //根据机器名获取InetAddress实例
//         InetAddress address1 = InetAddress.getByName("fan_0");
         InetAddress address2 = InetAddress.getByName("192.168.232.2");
         System.out.println("计算机: " + address2.getHostName());
         System.out.println("IP地址: " + address2.getHostAddress());

     }
}
