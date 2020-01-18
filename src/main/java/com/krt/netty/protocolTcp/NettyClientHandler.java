package com.krt.netty.protocolTcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例客户端处理类
 * @Date 2020/1/12 23:04
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    // 记录接收消息的次数
    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) {
        System.out.println("client received msg = "+ msg.toString());
        System.out.println("客户端接收消息的次数 = "+(++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 使用客户端发送10条MessageProtocol对象
        for(int i=0; i<5; i++){
            String str = "Hello Server"+i+". ";
            byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
            int len = bytes.length;

            MessageProtocol msg = new MessageProtocol();
            msg.setLen(len);
            msg.setContent(bytes);

            ctx.writeAndFlush(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
