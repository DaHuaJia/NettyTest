package com.krt.netty.protobuf2;

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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 读取客户端发送的MyDataInfo.MyMessage对象
        MyDataInfo.MyMessage message = (MyDataInfo.MyMessage) msg;
        if(message.getType() == MyDataInfo.MyMessage.DataType.StudentType){
            System.out.println("客户端发送的是Student对象。 id="+message.getStudent().getId()+" name="+message.getStudent().getName());

        }else if(message.getType() == MyDataInfo.MyMessage.DataType.WorkerType){
            System.out.println("客户端发送的是Worker对象。 age="+message.getWorker().getAge()+" name="+message.getWorker().getName());

        }else{
            System.out.println("类型错误。");
        }
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
