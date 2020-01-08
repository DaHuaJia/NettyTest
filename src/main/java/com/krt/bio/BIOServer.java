package com.krt.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author guoMingde
 * @Description java基础BIO网络编程, 同步阻塞式IO实例
 * @Date 2020/1/7 20:42
 */
public class BIOServer {

    /**
     * 特点：
     * javaBIO是java最先出现的tcp网络连接方式，事件驱动、同步、阻塞。
     * 服务器的实现模式为一个连接对应一个线程，即客户端有连接请求时服务器就需要启动一个线程进行处理，
     * 如果这个连接不做任何事情也会造成不必要的线程开销
     * 适用场景：
     * BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源的要求比较高，并发局限于应用中，
     * JDK1.4 以前的唯一选择，程序简单易于理解。
     */

    public static void main(String[] args) throws IOException {
        // 创建一个线程池用于处理客户端连接
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建serverSocket，并监听6666端口
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动。。。。");
        while(true){
            /**
             * 此时，程序会一直阻塞在此处等待客户端连接，直到有客户端连接进来才会向下执行
             * System.out.println("等待连接。。。。")
             */
            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("有一个客户端连接进来了");

            // 开启一个子线程去维护和客户端之间的连接
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    /**
     * 处理和客户端之间的连接和通信
     * @param socket
     */
    public static void handler(Socket socket){
        try{
            // 新建字节数组用于保存客户端发送过来的数据
            byte[] bytes = new byte[1024];

            /**
             * 此时，如果客户端一直没有发送数据，子线程就会阻塞在此处知道接收到客户端数据
             * System.out.println("read....")
             */
            // 通过socket获取一个输入流
            InputStream inputStream = socket.getInputStream();
            // 循环该输入流获取其全部字节
            while (true){
                // 每读取一个字节就存入bytes，并返回读取的字节数 等于-1表示读取完毕
                int read = inputStream.read(bytes);
                if(read != -1){
                    // 输出线程的信息
                    System.out.print("线程 "+Thread.currentThread().getId()+"-"+Thread.currentThread().getName()+" = ");
                    // 输入读取的数据，字节数组转string，从0至read
                    System.out.println(new String(bytes,0,read));

                }else{
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // 完成之后关闭通道连接
                System.out.println("连接关闭。");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 客户端使用window自带的telnet
     * cmd命令如下：telnet [IP地址] [端口号]
     * telnet 127.0.0.1 6666
     * ctrl + [ + ] 即可进入telnet 命令行，通过send message 即可发送数据
     */

}
