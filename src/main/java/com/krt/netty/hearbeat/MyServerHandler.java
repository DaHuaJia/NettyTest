package com.krt.netty.hearbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author gmd
 * @Description Netty心跳检测机制处理类
 * @Date 2020/1/16 17:21
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 进一步处理心跳事件，IdleStateEvent触发该方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        // 判断是否是该类的对象
        if(evt instanceof IdleStateEvent){
            IdleStateEvent eventType = (IdleStateEvent) evt;

            // 根据不同的超时类型做不同的处理
            switch (eventType.state()){
                case READER_IDLE:
                    System.out.println(ctx.channel().id()+"读空闲"); break;

                case WRITER_IDLE:
                    System.out.println(ctx.channel().id()+"写空闲"); break;

                case ALL_IDLE:
                    System.out.println(ctx.channel().id()+"读写空闲"); break;
            }
        }
    }

    /**
     * 客户端可以使用群聊天系统的客户端
     */

}
