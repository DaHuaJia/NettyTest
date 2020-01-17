package com.krt.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author gmd
 * @Description
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

}
