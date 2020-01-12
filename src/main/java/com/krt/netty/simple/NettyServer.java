package com.krt.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author gmd
 * @Description Netty简单服务端实例
 * @Date 2020/1/12 21:31
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        /**
         * 创建两个线程组 bossGroup和workerGroup
         * bossGroup只处理连接请求，真正的和客户端进行业务处理的是workerGroup
         * 两个都是无限循环
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建服务端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        /**
         * option()主要是针对bossGroup设置参数的，childOption()主要是针对workerGroup设置参数的
         */
        // 使用链式编程来进行设置
        // 设置两个线程组
        bootstrap.group(bossGroup, workerGroup)
                // 使用NioSocketChannel作为服务器端的通道实现类型
                .channel(NioServerSocketChannel.class)
                // 设置线程对列等待连接的个数
                .option(ChannelOption.SO_BACKLOG, 128)
                // 设置保持活跃连接状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 给workerGroup的EventLoop对应的管道设置处理器
                // 创建一个通道初始化对象（匿名对象）
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 给pipeline设置处理器，此处不设置，为null
                        ch.pipeline().addLast(null);
                    }
                });

        System.out.println("服务器准备成功。。。");

        // 启动服务器，绑定端口并开启同步，生成一个ChannelFuture对象
        ChannelFuture cf = bootstrap.bind(6666).sync();
        // 对关闭通道进行监听
        cf.channel().closeFuture().sync();

    }
}
