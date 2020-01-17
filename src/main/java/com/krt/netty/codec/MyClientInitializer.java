package com.krt.netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/17 21:37
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个出站的handler对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        // 加入自定义的handler 处理业务
        pipeline.addLast(new MyClientHandler());
    }

}
