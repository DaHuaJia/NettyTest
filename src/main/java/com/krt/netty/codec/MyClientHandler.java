package com.krt.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/17 21:56
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive 发送数据");
        // 发送一个Long类型数据
        // ctx.writeAndFlush(123456L);

        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdefghhgfedcba", CharsetUtil.UTF_8));
    }

}
