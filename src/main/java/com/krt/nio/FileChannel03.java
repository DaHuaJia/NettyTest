package com.krt.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author guoMingde
 * @Description FileChannel 文件拷贝
 * @Date 2020/1/8 20:11
 */
public class FileChannel03 {

    public static void main(String[] args) throws Exception {

        // 创建一个文件的输入流并获取其通道
        FileInputStream fileInputStream = new FileInputStream("F:/杂货间/krt.txt");
        FileChannel fileInput = fileInputStream.getChannel();
        // 创建一个文件的输出流并获取其通道
        FileOutputStream fileOutputStream = new FileOutputStream("F:/杂货间/1.txt");
        FileChannel fileOutput = fileOutputStream.getChannel();

        // 创建一个缓存区
        ByteBuffer buffer = ByteBuffer.allocate(5);

        // 循环读取文件的数据并写入缓冲区
        while (true){
            // 将上一次读取的数据擦除
            buffer.clear();
            // 读取文件数据，并写入缓冲区。如果有数据写入缓冲区则返回其写入的大小，如果没有则返回-1，表示文件读取完成，退出循环。
            int read = fileInput.read(buffer);
            if(read == -1){
                break;
            }
            // 缓冲区读写转换
            buffer.flip();
            // 将缓冲区的数据写入文件
            fileOutput.write(buffer);
        }

        // 文件读取完成之后，关闭输入输出流
        fileInput.close();
        fileOutput.close();
    }
}
