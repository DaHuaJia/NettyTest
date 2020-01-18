package com.krt.netty.protocolTcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author gmd
 * @Description Netty粘包和拆包实例服务端启动类
 * @Date 2020/1/12 21:31
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入自定义消息协议的解码器handler 入站
                            pipeline.addLast("ServerDecoder", new MyMessageDecoder());

                            // 加入自定义消息协议的编码器handler 出站
                            pipeline.addLast("ServerEncoder", new MyMessageEncoder());

                            // 加入自定义业务逻辑处理器
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("服务器准备成功");
            ChannelFuture cf = bootstrap.bind(1234).sync();
            cf.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
