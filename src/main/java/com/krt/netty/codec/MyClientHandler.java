package com.krt.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty编解码器实例 服务端初始化类
 * @Date 2020/1/17 21:56
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) {
        System.out.println("received server msg = "+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive 发送数据");
        // 发送一个Long类型数据
         //ctx.writeAndFlush(123456L);

        // 发送一个字符串
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdefghhgfedcba", CharsetUtil.UTF_8));

        /**
         * 我们可以通过对比，测试分别发送Long 和 字符串的输出情况。
         */
    }

}
