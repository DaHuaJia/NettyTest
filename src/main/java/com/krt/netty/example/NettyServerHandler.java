package com.krt.netty.example;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author gmd
 * @Description taskQueue自定义任务
 * @Date 2020/1/12 22:32
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        /**
         * 如果说，我们的此处的业务比较耗时，那么我们可以选择异步执行该业务，
         * 只需要提交该channel到对应NIOEventLoop的taskQueue中。
         * 我们可以提交多个任务到taskQueue中，只要复制下面的代码块，run()中写入你的业务即可
         */
        // 用户自定义普通任务。将耗时任务提交到对应NIOEventLoop的taskQueue中
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟任务耗时
                    Thread.sleep(5000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        /**
         * 除了上述方法可以异步执行业务逻辑外，scheduleTaskQueue()也可以达到异步效果，
         * 该方法作用是让业务延时多长时间执行，是一个延时执行函数，并且也是异步执行。
         */
        // 用户自定义定时任务。将耗时任务提交提交到对应NIOEventLoop的scheduleTaskQueue中延时执行
        // 将run()中的业务延时5秒异步执行
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8));
            }
        }, 5, TimeUnit.SECONDS);

    }

}
