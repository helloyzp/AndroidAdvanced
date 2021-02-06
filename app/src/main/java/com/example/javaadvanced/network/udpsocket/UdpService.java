package com.example.javaadvanced.network.udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 简单的UDP通信的服务端
 */
public class UdpService {

    public static void main(String... args) throws IOException {

        // 创建DatagramSocket，监听指定的端口
        // 因为UDP是无连接的，所以只要监听指定的端口的数据就可以了，
        // 不需要像TCP那样监听到客户端连接到指定端口后需要分配一个Socket连接
        DatagramSocket datagramSocket = new DatagramSocket(12307);

        byte[] bytes = new byte[1024];
        //UDP与TCP的Socket接收数据略有区别，需要创建一个DatagramPacket消息数据报对象，用于接收消息
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        //接收数据，receive()方法进行阻塞等待读取消息
        datagramSocket.receive(datagramPacket);

        System.out.println("接收到的数据： " + new String(datagramPacket.getData()));


    }


}
