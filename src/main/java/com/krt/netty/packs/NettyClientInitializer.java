package com.krt.netty.packs;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例客户端配置类
 * @Date 2020/1/18 14:10
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch){
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyClientHandler());
    }

}
