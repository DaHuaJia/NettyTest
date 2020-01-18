package com.krt.netty.protocolTcp;

import io.netty.util.CharsetUtil;

/**
 * @Author gmd
 * @Description 自定义消息协议
 * @Date 2020/1/18 14:45
 */
public class MessageProtocol {
    /**
     * 为解决TCP连接中，数据的粘包和拆包问题，创建一个消息协议类，包含消息长度和消息内容。
     * 同时也需要自定义对应的编解码器。
     */

    /**
     * 由于已经获取到了数据的长度，因此在从管道中读取数据时，只需要读取确定的数据即可，便不存在拆包和粘包的问题
     */

    private int len;
    private byte[] content;


    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{ len="+len+", content="+new String(content, CharsetUtil.UTF_8)+"}";
    }

}
