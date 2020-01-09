package com.krt.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author guoMingde
 * @Description MappedByteBuffer实例
 * @Date 2020/1/9 13:56
 */
public class MappedByteBufferTest {

    /**
     * MappedByteBuffer可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
     */
    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("F:/杂货间/krt.txt", "rw");
        // 获取对应通道
        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * 参数1：使用哪种模式  FileChannel.MapMode.READ_WRITE 表示读写结合
         * 参数2：可直接修改的起始位置
         * 参数3：需要映射到内存的大小（不是索引位置）
         * fileChannel.map() 会将指定区间段的文件内容放到内存中，并返回该内存块对象
         */
        MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        // 即可在内存中操作该文件，操作结果会自动同步映射到磁盘文件
        byteBuffer.put(0, (byte)'G');
        byteBuffer.put(3, (byte)'9');
        byteBuffer.put(4, (byte)'G');

        /**
         * 超过映射内容的位置操作会抛IndexOutOfBoundsException异常
         * byteBuffer.put(5, (byte)'G')
         */
        randomAccessFile.close();

    }

}
