package com.krt.netty.protobuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description Netty发送POJO对象服务端处理类
 * @Date 2020年1月17日14:46:18
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 也可以直接指定接收对象的StudentPOJO.Student
     * public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
     *
     * }
     */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 读取客户端发送的StudentPOJO.Student对象
        StudentPOJO.Student student = (StudentPOJO.Student) msg;

        System.out.println("客户端发送的POJO类为：id="+student.getId()+" name="+student.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭通道
        ctx.close();
    }

}
