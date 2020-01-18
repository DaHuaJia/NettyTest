package com.krt.netty.protocolTcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
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
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入自定义消息协议的编码器handler 出站
                            pipeline.addLast("ClientEncoder", new MyMessageEncoder());

                            // 加入自定义消息协议的解码器handler 入站
                            pipeline.addLast("ClientDecoder", new MyMessageDecoder());

                            // 加入自定义业务逻辑处理handler
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端准备完成");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 优雅关闭
            eventExecutors.shutdownGracefully();
        }
    }
}
