package com.krt.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author gmd
 * @Description Netty处理HTTP请求实例服务管道配置器
 * @Date 2020/1/14 20:56
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 向管道加入处理器

        // 加入httpServerCodec（Netty提供的处理http请求的编解码器）  codec=coder+decoder
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());

        pipeline.addLast("myTestHttpServerCodec", new HttpServerHandler());
    }

}
