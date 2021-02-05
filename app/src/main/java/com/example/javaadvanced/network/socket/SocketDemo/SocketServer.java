package com.example.javaadvanced.network.socket.SocketDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String... args) throws IOException {
        //1 创建一个服务端的ServerSocket，指定一个端口，监听此端口
        ServerSocket serverSocket = new ServerSocket(12346);
        //2 调用accept方法等待客户端连接
        System.out.println("调用accept之前...");
        Socket socket = serverSocket.accept();//accept()方法会一直阻塞，直到有客户端连接到服务端
        System.out.println("socket建立...");
        //3 连接后获取输入流，输出流
        InputStream is = socket.getInputStream();//获取输入流，用于读取客户端发过来的信息
        OutputStream os = socket.getOutputStream();//获取输出流，用于向客户端发送信息

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        while ((data = br.readLine()) != null) { //读取客户端发过来的信息
            System.out.println("客户端发送过来的信息: " + data);
        }

        //向客户端发送信息
         // ...

        socket.shutdownInput();
        socket.close();


    }

}
