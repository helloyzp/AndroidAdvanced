package com.example.javaadvanced.network.udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 简单的UDP通信的客户端
 */
public class UdpClient {
    public static void main(String... args) throws IOException {
        String msg = "Hello sevice";

         // 创建DatagramSocket，指定一个端口，如果不指定端口，则系统会自动分配一个空闲端口
        DatagramSocket datagramSocket = new DatagramSocket();

        // 创建datagramPacket用于发送信息
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getLocalHost(), 12307);

        // 发送消息
        datagramSocket.send(datagramPacket);

    }
}
