package com.krt.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @Author gmd
 * @Description Netty发送POJO对象服务端初始化类
 * @Date 2020年1月17日14:46:13
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {

        /**
         * Netty 本身自带的 ObjectDecoder 和 ObjectEncoder 可以用来实现 POJO 对象或各种业务对象的编码和解码，
         * 底层使用的仍是 Java 序列化技术 , 而Java 序列化技术本身效率就不高，存在如下问题：
         * 1.无法跨语言
         * 2.序列化后的体积太大，是二进制编码的5倍多。
         * 3.序列化性能太低
         */

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 在pipeline加入ProtobufDecoder解码器。需要指定对那种对象进行解码
                            pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            // 给pipeline设置处理器
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("服务器准备成功。。。");

            ChannelFuture cf = bootstrap.bind(1234).sync();
            cf.channel().closeFuture().sync();

        }finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
