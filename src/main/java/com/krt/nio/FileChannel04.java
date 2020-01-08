package com.krt.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Author guoMingde
 * @Description transferFrom() 用法实例
 * @Date 2020/1/8 22:03
 */
public class FileChannel04 {

    public static void main(String[] args) throws Exception {
        // 创建相关的输入输出流
        FileInputStream fileInputStream = new FileInputStream("F:/杂货间/QQ.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("F:/杂货间/1.jpg");

        // 获取对应的通道
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        /**
         * transferFrom() 从目标通道中复制数据到当前通道
         */
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
