package com.krt.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/17 21:53
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * if (this.acceptOutboundMessage(msg)) {
     * @param ctx
     * @param msg
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf byteBuf) {
        System.out.println("MyLongToByteEncoder encoder编码器被调用");
        System.out.println("msg = "+msg);
        byteBuf.writeLong(msg);
    }

}
