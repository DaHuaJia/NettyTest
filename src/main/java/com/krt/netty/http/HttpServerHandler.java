package com.krt.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Author gmd
 * @Description Netty处理HTTP请求实例服务处理器
 * 1.SimpleChannelInboundHandler 是ChannelInboundHandlerAdapter的子类
 * 2. HttpObject是客户端（浏览器）和服务端互相通讯的数据封装的类
 * @Date 2020/1/14 20:54
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 当客户端有数据调用该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断msg是不是httpRequest请求
        if(msg instanceof HttpRequest){
            System.out.println("msg的类型= "+msg.getClass());
            System.out.println("客户端地址= "+ctx.channel().remoteAddress());
            // System.out.println("消息为= "+msg.toString())

            // 获取请求头
            HttpRequest request = (HttpRequest) msg;
            // 获取uri
            URI uri = new URI(request.uri());
            // 通过uri，过滤特定的请求
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了 favicon.ico，不做响应");
                return ;
            }
            System.out.println(uri.toString());

            // 回复消息给浏览器，http协议
            ByteBuf content = Unpooled.copiedBuffer("Hello I am Server.", CharsetUtil.UTF_8);

            // 构造一个http的响应，即httpResponse，包括 http版本，http状态码，内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 设置响应的类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            // 设置响应的内容的长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构造好的response返回
            ctx.writeAndFlush(response);
        }
    }

}
