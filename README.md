# Netty 学习
> 之前通过一个物联网项目和阅读《Netty实战》，学习了部分Netty的知识，对于Netty的一些术语名词和基础的服务端和客户端的搭建有一定的了解。  
> 由于毕业设计的项目我尝试搭建一个高并发高可用的IOT云平台，仅凭目前的了解程度远远不够，因此，带着部分基础系统的学习Netty，为毕设打基础，   
> 同时拓宽自己的技术知识面。愿学完尚硅谷的学习视频，我能对Netty有个更全面的掌握和使用。  
> 以下是练习项目包和文件介绍。 

- - - 
## bio 
1. BIOServer
> java基础BIO网络编程同步阻塞式IO实例，是一个服务端，客户端可以使用windows自带的telnet，telnet的用法见代码注释。

## nio
1. BasicBuffer 
> Buffer的简单使用实例，包括创建、读写转换和参数说明 
2. ReadOnlyBuffer
> ReadOnlyBuffer（只读Buffer）的简单使用实例，介绍ReadOnlyBuffer，创建ReadOnlyBuffer。
3. ByteBufferPutGet
> ByteBuffer的创建和使用实例，介绍ByteBuffer按类型存入和读取的特点和注意事项。
4. FileChannel01
> NIO ByteBuffer和FileChannel实例，将字符串通过FileChannel写入到文件中
5. FileChannel02
> NIO ByteBuffer和FileChannel实例，将文件内容读入到ByteBuffer并显示
6. FileChannel03
> NIO ByteBuffer和FileChannel实例，文件拷贝，将一个文件的内容读入到缓存区，然后再从缓存区写入数据到另一个文件
7. FileChannel04 
> NIO transferFrom()函数的用法和实例，从目标通道中复制数据到当前通道，文件拷贝
8. MappedByteBufferTest
> MappedByteBuffer实例，可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
9. ScatterAndGather 
> SocketChannel读取客户端消息写入Buffer数组实例，演示read()和write()方法，可以将数据分散(Scattering)写入和汇总(Gathering)读取
### weChat
1. GroupChatServer
> NIO多人聊天系统实例 服务端，提示用户上线和下线，将和用户上下线数据和发送的消息发送给其他用户
2. GroupChatClient
> NIO多人聊天系统实例 客户端，连接服务端，接收服务端发送的消息并显示，获取输入的数据发送给服务端
### zerocopy
1. OldIOServer 和 OldIOClient
> 普通javaIO拷贝文件实例，客户端通过字节流和socket将文件数据发送给服务端，服务端接收数据并缓存到内存中
2. NewIOServer 和 NewIOServer
> javaNIO 零拷贝实例，客户端通过FileChannel和transferTo将文件数据发送给服务端，服务端接收数据并缓存到内存中

- - - 
