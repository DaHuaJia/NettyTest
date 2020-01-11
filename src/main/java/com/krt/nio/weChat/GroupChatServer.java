package com.krt.nio.weChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author gmd
 * @description 多人聊天服务端
 * @date 2020年1月11日11:59:21
 */
public class GroupChatServer {
    /**
     * 定义属性
     */
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    /**
     * 构造函数，初始化属性
     */
    public GroupChatServer() {
        try {
            // 初始化选择器
            selector = Selector.open();
            // 初始化服务端套接字通道
            listenChannel =  ServerSocketChannel.open();
            // 启动服务端通道并绑定接口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            System.out.println("======= 服务端已启动");
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该listenChannel注册到selector, 并指定关注事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听函数，监听服务端通道事件
     */
    public void listen() {
        try {
            while (true) {
                // 监听服务端通道，判断是否有关注的事件发生
                int count = selector.select();
                if(count > 0) {
                    // 迭代器，获得selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 判断事件发生的类型 连接事件
                        if(key.isAcceptable()) {
                            // 建立客户端SocketChannel
                            SocketChannel sc = listenChannel.accept();
                            // 设置为非阻塞
                            sc.configureBlocking(false);
                            // 将该客户端通道注册到selector，并设置关注事件为读事件
                            sc.register(selector, SelectionKey.OP_READ);
                            // 提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }
                        // 通道发送read事件，即通道是可读的状态
                        if(key.isReadable()) {
                            readData(key);
                        }
                        // 从迭代器中删除该key，防止重复处理
                        iterator.remove();
                    }
                } else{
                    System.out.println("等待....");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            System.out.println("===== 异常");
        }
    }

    /**
     * 读取客户端发送的数据
     * @param key
     */
    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            // 获取到关联的channel
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(100);
            int count = channel.read(buffer);
            // 根据count的值做处理
            if(count > 0) {
                // 把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                // 输出该消息
                System.out.println(channel.getRemoteAddress()+" ===>"+msg.trim());
                // 向其它的客户端转发消息(去掉自己)
                sendInfoToOtherClients(msg, channel);
            }

        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            }catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其它客户(通道)
     * @param msg
     * @param self
     * @throws IOException
     */
    private void sendInfoToOtherClients(String msg, SocketChannel self ) throws IOException{
        // 将msg存储到buffer
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        // 遍历所有注册到selector上的 SocketChannel,并排除自己
        for(SelectionKey key: selector.keys()) {
            //通过key取出对应的SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel)targetChannel;
                //将buffer的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        // 启动监听
        groupChatServer.listen();
    }

}

