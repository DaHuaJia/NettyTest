package com.krt.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author gmd
 * @Description NIO服务端实例
 * @Date 2020/1/10 11:35
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        // 创建ServerSocketChannel (服务端通道)
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 监听服务端TCP通道连接，并绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 创建Selector (通道选择器)
        Selector selector = Selector.open();
        // 将 serverSocketChannel注册到selector上，并设置监听事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while(true){
            // 监听1秒，判断没有关注的监听事件发生
            if(selector.select(3000) == 0){
                System.out.println("通道没有OP_ACCEPT（连接）事件发生");
                continue;
            }

            /**
             * 如果监听返回大于0 就说明通道发生了指定的事件，那么，我们就可以通过selector.selectedKeys()方法
             * 即可获取到发生关注事件的通道Key的集合，通过通道key即可得到对应的通道
             */
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 创建迭代器，使用迭代器遍历selectionKeys集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()){
                // 获取到SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据key判断对应的通道发生的事件类型
                // OP_ACCEPT: 有新的客户端连接
                if(key.isAcceptable()){
                    // 对该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("connection is success.="+socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    // 将当前的socketChannel 注册到selector上，并绑定一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(50));

                    // OP_READ: 客户端发送消息进来，读事件
                }else if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到该通道的Buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from client message = "+ new String(buffer.array()));
                    // 需要将上一次写入该通道缓存的数据清除
                    buffer.clear();
                    // 向客户端回告信息
                    channel.write(ByteBuffer.wrap("Hello Client.".getBytes()));
                }

                // 手动从集合中移除当前的selectionKeys，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
