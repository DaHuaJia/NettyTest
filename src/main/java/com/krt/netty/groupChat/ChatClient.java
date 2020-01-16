package com.krt.netty.groupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.util.Scanner;

/**
 * @Author gmd
 * @Description Netty多人聊天客户端启动类
 * @Date 2020/1/16 10:42
 */
public class ChatClient {
    /**
     * 属性
     */
    private final String host;
    private final int port;

    /**
     * 构造函数，初始化属性
     * @param host
     * @param port
     */
    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     *初始化方法
     */
    public void run() throws InterruptedException {
        // 创建客户端线程组
        EventLoopGroup executors = new NioEventLoopGroup();
        try{
            // 创建客户端引导器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(executors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });

            // 异步启动连接服务端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // 监听是否启动
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("客户端启动成功。");
                    }else{
                        System.out.println("客户端启动失败。");
                    }
                }
            });
            Scanner scanner = new Scanner(System.in);
            // 获取客户端channel
            Channel channel = channelFuture.channel();
            while (true){
                // 读取控制台输入并写入通道
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        }finally {
            executors.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatClient("127.0.0.1", 1234).run();
    }

}
