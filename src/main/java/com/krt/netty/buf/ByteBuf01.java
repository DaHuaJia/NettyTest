package com.krt.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author gmd
 * @Description Netty ByteBuf基础实例01
 * @Date 2020/1/15 16:24
 */
public class ByteBuf01 {
    public static void main(String[] args) {
        // 创建一个ByteBuf，该对象包含一个数组arr，是一个byte[10]
        ByteBuf buf = Unpooled.buffer(10);

        for(int i=0; i<10; i++){
            // 如果i没有超过byte的长度，会自动强转
            buf.writeByte(i);
        }

        // ByteBuf不需要使用javaNIO中ByteBuffer的flip()方法进行读写转换
        /**
         * 因为其底层维护了readerIndex（下一个读取的位置） 和 writeIndex（下一个写入的位置）
         * 通过readerIndex、writerIndex和capacity将buffer分成了三个区域
         * 0 至 readerIndex 已经读取的位置
         * readerIndex 至 writerIndex 可读的位置
         * writerIndex 至 capacity 可写的位置
         */

        System.out.println("buf的容量为= "+buf.capacity());

        for(int j=0; j<buf.capacity(); j++){
            System.out.println(buf.getByte(j));
        }

    }
}
