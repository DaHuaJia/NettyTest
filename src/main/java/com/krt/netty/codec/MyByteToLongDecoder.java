package com.krt.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author gmd
 * @Description Netty编解码器实例 解码器
 * @Date 2020/1/17 21:19
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decode会根据接收到的数据被调用多次，直到确定没有新的元素被添加到list 或者 ByteBuf没有更多的可读字节为止
     * 如果list不为空，就会将list中的内容传递给下一个handler，且该handler的方法也会被调用多次
     * @param ctx 上下文对象
     * @param byteBuf 入站的ByteBuf
     * @param list 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        System.out.println("MyByteToLongDecoder decode 解码器被调用");
        // 一个Long有8个字节，需要判断有8个字节才能读写一个Long
        // readableBytes() 获取当前byteBuf有几个可读字节
        if(byteBuf.readableBytes() >= 8){
            list.add(byteBuf.readLong());
        }
    }

    /**
     * 例如：
     * 如果客户端发送的消息并非Long类型，而是字符串"abcdefghhgfedcba"时，该字符串有16个字符，
     * 那么该解码器的decode()方法会被调用两次，且每次截取的8个字符读取，读取完成之后交给下一个handler处理，依次循环。
     *
     * 同样的，如果客户端发送的数据是null，decode()方法就会被等待，直到通道中有8个字符，如果满足了8个字符，
     * 但是，如果decode()方法结束后 list 为空，那么它也不会进入下一个handler。
     */

}
