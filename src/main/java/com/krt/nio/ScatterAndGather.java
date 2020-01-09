package com.krt.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;


/**
 * @Author guoMingde
 * @Description Scattering 和 Gathering 实例
 * @Date 2020/1/9 16:59
 */
public class ScatterAndGather {
    /**
     * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入 （分散）
     * Gathering：从buffer读取数据时，可以采用buffer数组，依次读取
     */
    public static void main(String[] args) throws Exception{
        // 通过open()方法创建一个服务端ServerSocketChannel套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 封装端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);

        // 一个通道对应一个socket，获取该socket并绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        // 监听，等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 一次读取/写入数据的最大长度
        int maxLength = 8;

        /**
         * buffer数组的总大小也为8，因此一次while循环，可以存满该buffer数组
         */
        while (true){
            // 当前已读取的长度
            int sum = 0;
            while (sum < maxLength){
                // 将客户端发送的数据读取到buffer数组中
                Long read = socketChannel.read(buffers);
                sum += read;
                System.out.println("read="+sum);
            }

            // 读写转换
            Arrays.asList(buffers).forEach(buffer -> buffer.flip());

            // 当前已写入的长度
            long total = 0;
            while(total < maxLength){
                // 将buffer数组中的数据写入通道返回给客户端
                long write = socketChannel.write(buffers);
                total += write;
                System.out.println("write="+total);
            }

            // 一次完成之后，清空buffer数组
            Arrays.asList(buffers).forEach(buffer -> {buffer.clear();});
        }

        /**
         * 客户端使用 telnet
         * 客户端发送一条数据，如果数据量小于8，那个一次while循环即可将数据全部读取写进缓存并返回给客户端，
         * 如果数据量大于8，那么在读取完8个数据之后，写进缓存并返回给客户端，然后再次读取数据。
         *
         * 内层while的主要作用就是每次让buffer全部存满，然后在返回给客户端，其实内层第二个while循环是没有必要的
         *
         */
    }
}
