package com.krt.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author guoMingde
 * @Description 将文件中的内容输出显示到屏幕上
 * @Date 2020/1/8 14:24
 */
public class FileChannel02 {

    public static void main(String[] args) throws Exception {
        // 获取文件句柄
        File file = new File("F:/杂货间/krt.txt");
        // 创建文件的输入流
        FileInputStream fileInputStream = new FileInputStream(file);

        // 获取对应的fileChannel
        FileChannel fileChannel = fileInputStream.getChannel();
        // 创建ByteBuffer缓冲区，大小和文件数据相同
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        // 将文件内容读入缓冲区
        fileChannel.read(buffer);

        // 输出文件内容
        System.out.println(new String(buffer.array()));
        // 关闭输入流
        fileInputStream.close();

    }
}
