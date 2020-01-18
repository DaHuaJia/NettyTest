package com.krt.netty.protocolTcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/18 14:56
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        System.out.println("MyMessageDecoder解码器 decode() 方法被调用");
        int len = in.readInt();
        byte[] content = new byte[len];

        in.readBytes(content);

        MessageProtocol msg = new MessageProtocol();
        msg.setLen(len);
        msg.setContent(content);
        out.add(msg);

    }

}
