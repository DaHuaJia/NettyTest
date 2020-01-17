package com.krt.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author gmd
 * @Description
 * @Date 2020/1/17 21:27
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    // 自定义初始化类
                    .handler(new MyClientInitializer());


            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
            System.out.println("启动成功");
            channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }

    }
}
