# Netty 学习
> 之前通过一个物联网项目和阅读《Netty实战》，学习了部分Netty的知识，对于Netty的一些术语名词和基础的服务端和客户端的搭建有一定的了解。  
> 由于毕业设计的项目我尝试搭建一个高并发高可用的IOT云平台，仅凭目前的了解程度远远不够，因此，带着部分基础系统的学习Netty，为毕设打基础，   
> 同时拓宽自己的技术知识面。愿学完尚硅谷的学习视频，我能对Netty有个更全面的掌握和使用。  
> 以下是练习项目包和文件介绍。 

- - - 
## bio 
1. BIOServer
> java基础BIO网络编程同步阻塞式IO实例，是一个服务端，客户端可以使用windows自带的telnet，telnet的用法见代码注释。

- - -
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
### nio.weChat
1. GroupChatServer
> NIO多人聊天系统实例 服务端，提示用户上线和下线，将和用户上下线数据和发送的消息发送给其他用户
2. GroupChatClient
> NIO多人聊天系统实例 客户端，连接服务端，接收服务端发送的消息并显示，获取输入的数据发送给服务端
### nio.zerocopy
1. OldIOServer 和 OldIOClient
> 普通javaIO拷贝文件实例，客户端通过字节流和socket将文件数据发送给服务端，服务端接收数据并缓存到内存中
2. NewIOServer 和 NewIOServer
> javaNIO 零拷贝实例，客户端通过FileChannel和transferTo将文件数据发送给服务端，服务端接收数据并缓存到内存中

- - - 
## netty
### netty.buf
1. ByteBuf01  
> ByteBuf基础实例，介绍ByteBuf的创建、容量和读写，简要说明其底层原理和结构。
2. ByteBuf02
> ByteBuf基础实例，通过字符串创建ByteBuf对象，说明其特殊性、编码格式及其常用方法。
### netty.simple
> Netty服务端和客户端的简单实现。客户端连接服务端成功之后发送 Hello Server. 给服务端。服务端收到数据后，返回 Hello Client. 
### netty.example
1. NettyServer
> ChannelFuture 的用法，异步处理耗时业务，绑定监听事件判断业务执行情况
2. NettyServerHandler
> taskQueue自定义任务。execute()和schedule()用法。将耗时事件交给taskQueue异步处理
### netty.groupChat
> Netty 群聊服务。客户端可以获取用户输入发送给服务端，服务端获得数据转发给其他用户。同时客户端可以感知用户上下线，并通知其他用户。  
### netty.http
> Netty处理HTTP请求，包括httpServerCodec编解码器，URI请求地址过滤，回复消息等，客户端用浏览器进行请求。
### netty.hearbeat
> Netty IdleStateHandler心跳监测机制 用法实例。Netty集成了通道连接空闲监听方法，对IdleStateEvent事件进行捕获处理。
### netty.websocket
> Netty WebSocket用法实例，分为服务端和html页面。
### netty.protobuf
> Netty发送简单的POJO对象实例。使用google的ProtoBuf，README.md中有ProtoBuf安装配置和用法说明。  
> 通过Student.proto，使用protoc命令即可生成StudentPOJO类。里面包含了服务端和客户端，  
> 客户端将Student对象发送给服务端，服务端接收到对象后打印其属性，并回告信息。
### netty.protobuf2
> Netty 发送多种POJO对象实例。ProtoBuf进阶版。一个 .proto 中包含了多个类，我们可以生成不同的类的对象，  
> 并传给服务端，服务端接收不同类的对象，并将接收结果打印出来。
### netty.codec
> Netty编解码器实例。新建服务端和客户端，加入自定义编解码器，该编解码器为Long转Byte。  
> 同时说明了编解码器的功能和用法，说明编解码器多次调用和不被调用的情况。
### netty.example
3. MyByteToLongDecoder
> 该实例是一个解码器，主要介绍ReplayingDecoder，ReplayingDecoder是对ByteToMessageDecoder的进一步封装，
> 通过实例说明其特殊功能和作用。
### netty.packs
> Netty数据粘包和拆包的问题，介绍粘包和拆包的情况和隐患。
### netty.protocolTcp
> Netty数据粘包和拆包解决办法，通过自定义消息协议和自定义编解码器处理粘包拆包问题。原理主要是创建消息协议对象，
> 在数据传送的过程中，发送数据的大小，那么接收方即可按照指定的大小接收数据，防止粘包和拆包。
