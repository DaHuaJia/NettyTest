package com.krt.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty发送POJO对象客户端处理类
 * @Date 2020年1月17日14:47:35
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 发送要给Student对象到服务端
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 创建一个Student对象
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(2020).setName("大话家").build();
        // 发送该对象
        ctx.writeAndFlush(student);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("server msg = "+buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("通道异常。。。");
        // 关闭通道
        ctx.close();
    }

}
