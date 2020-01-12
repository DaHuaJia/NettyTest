package com.krt.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty简单客户端处理器实例
 * @Date 2020/1/12 23:04
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client ctx= "+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Server.", CharsetUtil.UTF_8));

    }

    /**
     * 当通道有读取事件时触发该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("server msg = "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端地址: "+ctx.channel().remoteAddress());
    }

    /**
     * 通道发生异常时调用改方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("通道异常。。。");
        // 关闭通道
        ctx.close();
    }
}
