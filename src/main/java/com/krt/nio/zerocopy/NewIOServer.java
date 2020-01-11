package com.krt.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author gmd
 * @description javaNIO 零拷贝服务端
 * @date 2020年1月11日14:59:55
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception {
        // 新建服务端套接字通道实例
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 启动服务端，并绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(7001));
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            // 阻塞，等待客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            int sum = 0;
            // 循环读取数据，将数据读入buffer
            while (sum != -1) {
                try {
                    sum = socketChannel.read(byteBuffer);
                } catch (Exception ex) {
                    // ex.printStackTrace()
                    break;
                }
                // 倒带 position=0, mark作废
                byteBuffer.rewind();
            }
        }
    }
}
