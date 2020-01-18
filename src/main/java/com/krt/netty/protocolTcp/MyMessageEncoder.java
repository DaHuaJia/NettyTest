package com.krt.netty.protocolTcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/18 14:52
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) {
        System.out.println("MyMessageEncoder 编码器 encoder() 方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }

}
