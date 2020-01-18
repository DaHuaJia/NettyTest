package com.krt.netty.packs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例客户端启动类
 * @Date 2020/1/12 22:51
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(eventExecutors)
                    // 设置客户端通道的实现类型
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());

            System.out.println("客户端准备完成。。。");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            eventExecutors.shutdownGracefully();
        }
    }
}
