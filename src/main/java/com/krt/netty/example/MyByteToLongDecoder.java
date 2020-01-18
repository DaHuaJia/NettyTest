package com.krt.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/**
 * @Author gmd
 * @Description Netty编解码器实例 解码器 ReplayingDecoder实例
 * @Date 2020/1/17 21:19
 */
public class MyByteToLongDecoder extends ReplayingDecoder<Void> {

    /**
     * ReplayingDecoder扩展了ByteToMessageDecoder类，使用这个类，我们不必调用readableBytes()方法。
     * 参数S指定了用户状态管理的类型，其中Void代表不需要状态管理
     * @param ctx 上下文对象
     * @param byteBuf 入站的ByteBuf
     * @param list 集合，将解码后的数据传给下一个handler
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        // 在ReplayingDecoder不需要判断数据是否足够读取，内部会进行处理判断
        list.add(byteBuf.readLong());
    }

}
