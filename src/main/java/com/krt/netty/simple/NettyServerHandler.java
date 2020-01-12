package com.krt.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty服务端处理器实例
 * 1. 我们自定义的Handler需要继承Netty规定的其中一个HandlerAdapter，继承其规范和方法，这样我们自定义的Handler才能称之为Handler
 * @Date 2020/1/12 22:32
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道有读取事件时触发该方法（读取客户端发送的数据）
     * @param ctx 上下文对象，含有管道(pipeline)信息、通道(channel)信息、地址等
     * @param msg 客户端发送的数据，默认是Object
     * @throws Exception
     */
    /**
     * 管道pipeline和通道channel
     * pipeline主要是对业务逻辑进行处理，channel主要是对数据进行读写
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("server ctx="+ctx);
        // 将msg转成ByteBuf
        // ByteBuf是Netty提供的，不同于NIO的ByteBuffer，效率更高
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Client msg = "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());
    }

    /**
     * 通道数据读取完毕是调用该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        /**
         * writeAndFlush 是 write + flush
         * 我们需要对发送的数据进行编码
         */
        // 数据写入缓冲并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8));
    }

    /**
     * 通道发生异常时调用改方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭通道
        ctx.close();
    }

}
