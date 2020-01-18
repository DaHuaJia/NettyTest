package com.krt.netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author gmd
 * @Description Netty编解码器实例 服务端handler配置类
 * @Date 2020/1/17 21:16
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 自定义入站handler进行解码，加入自定义的解码器
         */
        pipeline.addLast(new MyByteToLongDecoder());

        /**
         * 自定义出站handler进行解码，加入自定义的编码器
         */
        pipeline.addLast(new MyLongToByteEncoder());

        // 自定义handler 业务逻辑处理
        pipeline.addLast(new MyServerHandler());

    }

}
