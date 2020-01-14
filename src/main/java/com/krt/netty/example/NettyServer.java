package com.krt.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @Author gmd
 * @Description Netty异步机制 ChannelFuture
 * @Date 2020年1月14日20:36:28
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        try{
            /**
             * Netty服务端初始化，这里主要演示ChannelFuture，省略其他代码
             */
            ServerBootstrap bootstrap = new ServerBootstrap();
            /**
             * Future的作用是用来保存异步操作的结果，在操作完成时通知应用程序，该对象可以看做是异步操作的结果的占位符。
             *
             * 当Future对象刚刚创建时，处于非完成状态，调用者可以通过返回的Future继承类的对象来获取操作过程中的执行状态，
             * 注册监听函数来执行完成之后的操作。、
             *
             * 异步处理不会造成线程的阻塞，相比于传统IO，在高并发的情况下，效果更好
             */
            ChannelFuture cf = bootstrap.bind(6666).sync();

            // 通过ChannelFuture对象注册监听器，当bind()方法执行完毕时，监听器被触发
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) {
                    /**
                     * isDone         当前操作是否完成
                     * isSuccess      当前操作是否成功
                     * getCause       当前操作失败的原因
                     * isCancelled    当前操作是否被取消
                     * addListener    注册监听器
                     */
                    if(cf.isSuccess()){
                        System.out.println("服务端监听端口开启成功。");
                    }else{
                        System.out.println("服务端监听端口开启失败。");
                    }
                }
            });

            // 也可以通过链式编程，直接进行监听
            /**
             * bootstrap.bind(6666).sync().addListener(new ChannelFutureListener() {
             *      @Override
             *      public void operationComplete(ChannelFuture channelFuture) throws Exception {
             *
             *      }
             * })
             */

        }finally {
            // 优雅关闭
        }
    }

}
