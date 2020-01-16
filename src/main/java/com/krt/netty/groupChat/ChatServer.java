package com.krt.netty.groupChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author gmd
 * @Description Netty多人聊天服务端启动类
 * @Date 2020/1/15 23:16
 */
public class ChatServer {
    /**
     * 监听端口
     */
    private int port;

    /**
     * 构造函数，初始化端口
     * @param port
     */
    public ChatServer(int port){
        this.port = port;
    }

    /**
     * 处理客户端请求
     */
    public void run(){
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入自己的handler
                            pipeline.addLast(new ChatServerHandler());

                        }
                    });

            try {
                // 启动监听
                ChannelFuture channelFuture = bootstrap.bind(port).sync();
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) {
                        if(channelFuture.isSuccess()){
                            System.out.println("服务端启动成功。 端口= "+port);
                        }else{
                            System.out.println("服务端启动失败。");
                        }
                    }
                });

                // 关闭监听
                ChannelFuture closeFuture = channelFuture.channel().closeFuture().sync();
                closeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if(channelFuture.isSuccess()){
                            System.out.println("服务端已关闭");
                        }else{
                            System.out.println("服务端关闭失败。");
                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new ChatServer(1234).run();
    }
}
