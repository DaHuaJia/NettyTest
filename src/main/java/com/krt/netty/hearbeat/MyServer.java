package com.krt.netty.hearbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author gmd
 * @Description Netty心跳检测机制初始化类
 * @Date 2020/1/16 15:25
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 在bossGroup中增加一个日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 在workerGroup中增加处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 增加Netty提供的空闲状态监测器
                             * readerIdleTime 表示多长时间没有读取客户端数据，超出时间就会发送一个特殊的心跳检测包
                             * writerIdleTime 表示多长时间没有写入数据给客户端，超出时间就会发送一个特殊的心跳检测包
                             * allIdleTime 表示多长时间没有读写数据，超出时间就会发送一个特殊的心跳检测包
                             * unit 时间单位
                             *
                             * 0表示禁用该事件，超出对应状态的指定的时间就是触发IdleStateEvent事件，
                             * IdleStateEvent事件发生后，就会将其传递给管道的下一个handler去处理，即触发下一个handler的userEventTriggered()
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 对心跳做进一步处理
                            pipeline.addLast(new MyserverHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(1234).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
