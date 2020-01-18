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
    // 记录接收消息的次数
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String str = new String(bytes, CharsetUtil.UTF_8);

        System.out.println("客户端接收消息的次数 = "+(++this.count));
        System.out.println("client received msg = "+str);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        /**
         * 客户端连续发送10条数据观察服务端的接收情况
         */
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

    /**
     * 通过观察，我们可以得知，当一次性接收一个较大的数据包时，通道会将该数据包进行拆包发送，导致接收方收到的数据失真。
     */
}
