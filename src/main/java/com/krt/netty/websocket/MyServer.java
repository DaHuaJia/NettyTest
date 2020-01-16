package com.krt.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author gmd
 * @Description Netty WebSocket长连接初始化类
 * @Date 2020年1月16日21:39:41
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
                            // 因为是基于http协议，因此要使用http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加ChunkedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * http数据在传输过程中是分段的，HttpObjectAggregator就是可以将多个段聚合起来。
                             * 实际上，当浏览器发送大量数据时，浏览器就会出现多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 对应的webSocket，它的数据是以帧（frame）的形式传递的。
                             * 浏览器发送webSocket请求时，使用ws协议，如 ws://localhost:1234/hello。
                             * WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 自定义handler，处理业务逻辑
                            pipeline.addLast(new MyServerHanlder());

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
