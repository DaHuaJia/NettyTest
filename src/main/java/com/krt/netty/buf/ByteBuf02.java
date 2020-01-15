package com.krt.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;

/**
 * @Author gmd
 * @Description Netty ByteBuf基础实例02
 * @Date 2020/1/15 22:47
 */
public class ByteBuf02 {
    public static void main(String[] args) {
        // 创建byteBuf，通过这种方式创建的ByteBuf，其容量大于其存储量，是其存储量的三倍
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);

        if(byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            // 编码格式要统一
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println("byteBuf= "+byteBuf);
            // 数组偏移量
            System.out.println(byteBuf.arrayOffset());
            // readerIndex
            System.out.println(byteBuf.readerIndex());
            // writerIndex
            System.out.println(byteBuf.writerIndex());
            // 容量
            System.out.println(byteBuf.capacity());
            // 可读的字节数
            System.out.println(byteBuf.readableBytes());

            // 读取一位字节出来，相应的其readerIndex 和 可读字节数都会变化
            // 输出其对应的ascii码，可以通过 (char)强转
            System.out.println(byteBuf.readByte());
            // 读取一位字节出来，其readerIndex 和 可读字节数都会不会变化
            System.out.println(byteBuf.getByte(0));

            // 按照某个范围区间读取
            System.out.println(byteBuf.getCharSequence(2, 6, CharsetUtil.UTF_8));
        }
    }
}
