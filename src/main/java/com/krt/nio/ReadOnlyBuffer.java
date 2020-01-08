package com.krt.nio;

import java.nio.ByteBuffer;

/**
 * @Author guoMingde
 * @Description 只读Buffer
 * @Date 2020/1/8 23:18
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        // 创建一个ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 存入数据
        for(int i=0; i<5; i++){
            buffer.put((byte)i);
        }

        buffer.flip();

        /**
         * 创建一个新的，只读的ByteBuffer来共享这个buffer的内容。
         * 我们可以随时将一个普通的Buffer调用asReadOnlyBuffer(),返回一个只读的Buffer但是不能将只读buffer转换为可写的buffer。
         */
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        // 查看只读Buffer的类型
        System.out.println(readOnlyBuffer.getClass());

        // readOnlyBuffer可以共享buffer中的内容
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }

        // 不能往这个只读buffer中添加数据，会抛出ReadOnlyBufferException异常
        readOnlyBuffer.put((byte)9);
    }
}
