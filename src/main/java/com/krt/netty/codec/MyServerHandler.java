package com.krt.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author gmd
 * @Description Netty编解码器实例 服务端处理器类
 * @Date 2020/1/17 21:23
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) {
        // 打印接收到的Long类型数据
        System.out.println("["+ctx.channel().id()+"] Long = "+msg);

        // 给客户端回送消息
        ctx.writeAndFlush(654321L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

}
