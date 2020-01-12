package com.krt.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author gmd
 * @Description Netty简单客户端实例
 * @Date 2020/1/12 22:51
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        // 客户端创建一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try{
            // 创建客户端启动对象，注意客户端使用的不是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();

            // 设置相关参数
            // 设置线程组
            bootstrap.group(eventExecutors)
                    // 设置客户端通道的实现类型
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 加入自己的处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端准备完成。。。");

            // 启动客户端去连接服务器端
            // 关于ChannelFuture要分析，涉及到Netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            eventExecutors.shutdownGracefully();
        }
    }
}
