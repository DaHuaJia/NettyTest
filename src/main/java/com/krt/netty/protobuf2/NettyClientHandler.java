package com.krt.netty.protobuf2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @Author gmd
 * @Description Netty发送POJO对象客户端处理类
 * @Date 2020年1月17日14:47:35
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 随机发送Student或Worker对象
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        int random = new Random().nextInt(2);
        MyDataInfo.MyMessage myMessage = null;
        if(random == 0){
            // 初始化成Student对象
            myMessage = MyDataInfo.MyMessage.newBuilder().
                    setType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder()
                            .setId(2020).setName("学生 大话家").build()
                    ).build();
        }else{
            // 初始化成Worker对象
            myMessage = MyDataInfo.MyMessage.newBuilder().
                    setType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder()
                            .setAge(24).setName("工程师 大话家").build()
                    ).build();
        }

        ctx.writeAndFlush(myMessage);
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
