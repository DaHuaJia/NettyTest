package com.krt.netty.packs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例服务端处理类
 * @Date 2020/1/12 22:32
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] buffers = new byte[msg.readableBytes()];
        msg.readBytes(buffers);

        // 将buffers转成字符串
        String str = new String(buffers, CharsetUtil.UTF_8);
        System.out.println("server received msg = "+str);
        System.out.println("count="+(++this.count));

        ByteBuf receive = Unpooled.copiedBuffer(UUID.randomUUID().toString()+" ", CharsetUtil.UTF_8);
        // 回送随机数据给客户端
        ctx.writeAndFlush(receive);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }

}
