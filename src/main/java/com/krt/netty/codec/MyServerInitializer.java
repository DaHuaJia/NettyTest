package com.krt.netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/17 21:16
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        // 自定义入站handler进行解码
        pipeline.addLast(new MyByteToLongDecoder());

        pipeline.addLast(new MyServerHandler());

    }

}
