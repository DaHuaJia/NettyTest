## protobuf的安装与配置

### 下载
>  可以去git上下载你需要的压缩包，建议3.x版本即可，解压即可用。  
> 下载地址：https://github.com/protocolbuffers/protobuf/tags  
> 在该项目的路径下，你可以可以看到其压缩包，protoc-3.6.1-win32.zip

### 解压和配置
> 将压缩包解压到一个没有英文的路径，将其 protoc.exe 的路径添加到系统变量Path中。  
> 例如：E:\Application\protoc-3.6.1-win32\bin  
> 完成之后，CMD命令，运行protoc 查看是否配置成功。

### 用法
> CMD命令进入需要编译的 xxx.proto 文件路径下，使用如下命令：  
> protoc --java_out=. xxx.proto  
> 若一切顺利，即可看到当前路径下新生成了一个.java文件。
> 文档：https://developers.google.com/protocol-buffers/docs/proto   