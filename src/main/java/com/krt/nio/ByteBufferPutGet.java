package com.krt.nio;

import java.nio.ByteBuffer;

/**
 * @Author guoMingde
 * @Description ByteBuffer put/get方法注意事项
 * @Date 2020/1/8 22:12
 */
public class ByteBufferPutGet {

    public static void main(String[] args) {
        // 创建一个ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(100);
        /**
         * 除了ByteBuffer之外，还有LongBuffer、CharBuffer、FloatBuffer、StringBuffer、ShortBuffer 等。
         */

        // 类型化方式放入数据
        buffer.putInt(10);
        buffer.putLong(1000);
        buffer.putChar('A');
        buffer.putShort((short) 9);

        buffer.flip();

        /**
         * ByteBuffer 按类型存入的就要按类型取出，否则抛BufferUnderFlowException异常
         * 取出时不传入下标则默认是读写转换后的position位置开始按不同的类型读取类型对应的长度
         */
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

        /**
         * 通过DEBUG我们可以清楚的看到，在缓冲区中并不是一个数据占一个存储单元，而是根据put选择的类型分配一个区间保存该变量，
         * 因此，通过指定下标获取对应的值时，我们往往无法准确获取该值
         * 同时，我们调用一个get方法后，该数据并没有从缓冲区中取出，仅是position改变了，实际上我们将position改回原来的值，依然可以获取到结果。
         */

    }

}
