package com.krt.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author guoMingde
 * @Description NIO中 ByteBuffer和FileChannel练习
 * @Date 2020/1/8 14:02
 */
public class FileChannel01 {

    /**
     * 将str中的内容输出到krt.txt中
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws Exception {
        String str = "Hello World.";
        // 创建一个文件输出流
        FileOutputStream fileStream = new FileOutputStream("F:/杂货间/krt.txt");

        // 获取对应的FileChannel，注意fileChannel的真是类型是FileChannelImpl
        FileChannel fileChannel = fileStream.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将str放入buffer
        buffer.put(str.getBytes());

        // 读写转换
        buffer.flip();

        // 将buffer中的数据写入到fileChannel
        fileChannel.write(buffer);
        // 关闭文件流
        fileStream.close();
    }

}
