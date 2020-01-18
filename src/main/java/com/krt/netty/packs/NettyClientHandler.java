package com.krt.netty.packs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例客户端处理类
 * @Date 2020/1/12 23:04
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String str = new String(bytes, CharsetUtil.UTF_8);

        System.out.println("client received msg = "+str);
        System.out.println("count="+(++this.count));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 使用客户端发送10条数据
        for(int i=0; i<10; i++){
            ByteBuf buffer = Unpooled.copiedBuffer("Hello Server"+i+". ", CharsetUtil.UTF_8);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
