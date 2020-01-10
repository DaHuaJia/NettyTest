package com.krt.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.Thread.sleep;

/**
 * @Author gmd
 * @Description NIO客户端实例
 * @Date 2020/1/10 14:25
 */
public class NIOClient {

    public static void main(String[] args) throws Exception{
        // 创建客户端通道
        SocketChannel socketChannel = SocketChannel.open();
        // 提供服务端的IP和端口
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",6666);
        // 设置为非阻塞
        socketChannel.configureBlocking(false);

        /**
         * 新创建的Channel都是未连接的，可以调用connect方法去连接，在连接之前尝试IO操作会导致NotYetConnectedException异常
         *
         * 阻塞模式下，线程在建立连接的时间段内都会保持阻塞状态，直到连接完成或抛出超时异常，因此，并不需要finishConnect()进行再次连接
         *
         * 非阻塞模式下，线程在建立连接的时间段内都会保持非阻塞状态，如果程序无法立即完成连接，那么就需要使用finishConnect()来完成连接
         */
        // 连接服务器
        if(!socketChannel.connect(socketAddress)){
            /**
             * 为了避免因无法及时连接导致的死循环，我们可在程序建立连接之前不设置非阻塞，在连接建立完成之后设置为非阻塞，
             * 即把 socketChannel.configureBlocking(false); 放在连接之后
             */
            while (!socketChannel.finishConnect()){
                System.out.println("连接失败，客户端不会阻塞，可以做其他事情。");
            }
        }

        System.out.println("连接成功。");
        // 向服务器端写入数据
        socketChannel.write(ByteBuffer.wrap("Hello World.".getBytes()));

        // 新建一个Buffer保存服务器端的数据
        ByteBuffer buffer2 = ByteBuffer.allocate(20);
        // 需要等服务器端数据完全写入才能读取，否则读取为空
        // 实际上，我们通过创建一个Selector 并绑定该通道即可不用sleep阻塞，通过监听判断是否服务端的事件是否完成，操作客户端类似。
        sleep(500);
        // 读取客户端的回告信息，并写入buffer2
        socketChannel.read(buffer2);
        // 显示客户端的回告信息
        System.out.println(new String(buffer2.array()));

        // 接收一个byte，避免因客户端掉线导致服务端退出
        System.in.read();
    }

    /**
     * public boolean connect(SocketAddress remote) throws IOException
     * 使底层Socket建立远程连接。当SocketChannel 处于非阻塞模式时，如果立即连接成功，该方法返回true，如果不能立即连接成功，该方法返回false,
     * 程序之后必须通过调用finishConnect()方法来完成连接。当SocketChannel处于阻塞模式，如果立即连接成功，该方法返回true，
     * 如果不能立即连接成功，将进入阻塞状态，直到连接成功，或者出现I/O异常。
     *
     * public boolean finishConnect( )throws IOException
     * 试图完成连接远程服务器的操作。在非阻塞模式下,建立连接从调用SocketChannel的connect()方法开始，到调用finishConnect()方法结束。
     * 如果finishConnect()方法顺利完成连接,或者在调用此方法之前连接已经建立,则finishConnect()方法立即返回true。
     * 如果连接操作还没有完成，则立即返回false;如果连接操作中遇到异常而失败，则抛出相应的I/O异常。
     *
     * 在阻塞模式下，如果连接操作还没有完成，则会进入阻塞状态，直到连接完成，
     */

}
