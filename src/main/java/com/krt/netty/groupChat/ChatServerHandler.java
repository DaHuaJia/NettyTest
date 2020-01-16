package com.krt.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author gmd
 * @Description Netty多人聊天服务端处理类
 * @Date 2020/1/16 9:37
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个Channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是一个全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 时间格式化处理
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将当前的channel加入到channelGroup中，并发送提示
     * @description 连接一旦被建立，首先触发执行该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 获取加入的通道的channel
        Channel channel = ctx.channel();
        // 向其他所有的通道推送上线提示，以下方法会自动遍历所有的channel，并发送消息
        channelGroup.writeAndFlush(sdf.format(new Date())+" [客户端 "+channel.id().hashCode()+" ] 加入聊天 ");
        // 将当前的channel交给channelGroup管理
        channelGroup.add(channel);
    }

    /**
     * 服务端提示用户上线
     * @description channel处于活动状态时触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(ctx.channel().remoteAddress()+" 上线了。。。");
    }

    /**
     * 服务端提示用户离线
     * @description channel处于非活动状态时触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        System.out.println(ctx.channel().remoteAddress()+" 离线了。。。");
    }

    /**
     * 有通道断开时，通知其他用户
     * @description 通道断开时触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date())+" [客户端 "+channel.id().hashCode()+" ] 退出聊天 ");
        // 通道连接断开后，channelGroup会自动将断开的channel从自身剔除
    }

    /**
     * 获取通道数据
     * @description 通道中有数据时触发该方法
     * @param ctx
     * @param str
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String str){
        // 获取当前的channel
        Channel channel = ctx.channel();

        // 遍历channelGroup，将消息排除发送方群发
        channelGroup.forEach(ch -> {
            if(ch != channel){
                ch.writeAndFlush("["+channel.id().hashCode()+"] "+str);
            }
        });
    }

    /**
     * 通道发生异常，关闭通道
     * @description 当通道发生异常时触发该方法
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 关闭通道
        ctx.close();
        cause.printStackTrace();
    }

}
