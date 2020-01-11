package com.krt.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author gmd
 * @description 普通javaIO拷贝文件_服务端
 * @date 2020年1月11日14:47:53
 */
public class OldIOClient {

    public static void main(String[] args) throws Exception {
        // 创建socket，连接服务端
        Socket socket = new Socket("localhost", 7001);

        // 文件路径 相对路径
        String fileName = "protoc-3.6.1-win32.zip";
        // 新建输入流
        InputStream inputStream = new FileInputStream(fileName);
        // 新建数据输出流
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        // 新建缓存区，将文件内容读到缓存区，然后写入通道
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        // 记录文件开始传输的时间
        long startTime = System.currentTimeMillis();

        // 文件内容读到缓存区
        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            // 将缓存区的内容写入通道
            dataOutputStream.write(buffer);
        }
        // 计算总时间
        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        // 关闭通道和输入输出流
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }

}
