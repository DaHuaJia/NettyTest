package com.krt.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author gmd
 * @Description Netty编解码器实例 编码器
 * @Date 2020/1/17 21:53
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 编码器实现
     * @param ctx
     * @param msg
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf byteBuf) {
        System.out.println("MyLongToByteEncoder encoder编码器被调用");
        System.out.println("编码 msg = "+msg);
        byteBuf.writeLong(msg);
    }

    /**
     * 编码器和解码器有所不同，无论通道中的数据长度如何，编码器都不会被重复调用，一次writeAndFlush()只会调用一次编码器。
     * 同时，如果writeAndFlush()写入的类型和编码器指定的类型不同，那么该编码器不会被调用。
     *
     * 因为其底层有if判断
     * if (this.acceptOutboundMessage(msg)) {
     *     // do something
     * }else{
     *
     * }
     *
     * 例如：
     * 该编码器指定的类型为Long，那么当写入的类型是String时，该编码器便不会被调用。
     */

    /**
     * 无论是编码器handler还是解码器handler，其接收到的数据类型必须与待处理的消息类型一致，否则该handler不会被执行。
     */

}
