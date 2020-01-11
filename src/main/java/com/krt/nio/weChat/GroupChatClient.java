package com.krt.nio.weChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author gmd
 * @description 多人聊天客户端
 * @date 2020年1月11日13:46:23
 */
public class GroupChatClient {
    /**
     * 定义相关的属性
     */
    private final String HOST = "127.0.0.1";
    private final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    /**
     * 构造函数，完成初始化工作
     *
     * @throws IOException
     */
    public GroupChatClient() throws IOException {
        // 初始化选择器
        selector = Selector.open();
        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 将channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    /**
     * 向服务器发送消息
     *
     * @param info
     */
    public void sendInfo(String info) {
        info = username + " 说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取从服务器端回复的消息
     */
    public void readInfo() {
        try {
            // 监听线程通道，判断服务端是否有消息返回
            int readChannels = selector.select();
            // 有可以用的通道
            if (readChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        // 得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        // 读取
                        sc.read(buffer);
                        // 把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                // 删除当前的selectionKey, 防止重复操作
                iterator.remove();
            } else {
                // System.out.println("没有可以用的通道...")
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();

        // 启动一个线程，每隔3秒读取从服务器发送数据
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        /**
         * 循环读取用户输入，发送给服务端
         */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }

}
