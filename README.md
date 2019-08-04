Spring Boot Netty Poc
---------------------------------------------

Start Server and Testing
---------------------------------------------
Run NettyServerPocApplication.class

Testing:
```
curl localhost:8080
curl localhost:8080/server/sendEchoRequest
curl localhost:8080/server/sendEchoResponse
```

Start client and Testing
---------------------------------------------
Run NettyClientPocApplication.class

Testing:
```
curl localhost:8081
curl localhost:8081/client/sendEchoRequest
curl localhost:8081/client/sendEchoResponse
```

客户端连接服务器后自动注册
---------------------------------------------
1、自动注册客户端ID到服务器  
2、服务器自动把客户端ID保存到ClientHolder中  
3、服务器可以通过ClientHolder向客户端下发消息  
4、TODO： 服务器通过业务逻辑（数据库内容）校验客户端ID的有效性  
5、TODO： 使用Key校验客户端登录动作  
6、TODO： 响应客户端消息中，携带SessionID，参与后续消息加密（秘钥Key+SessionID），提升安全性  
7、TODO： 客户的没有注册完成时，不应处理后续消息  
8、需要保存回话信息到Channel中，待调查最新版本的处理方式   


协议扩展
---------------------------------------------
1、在Core中定义协议消息  
2、在Core中的Command枚举类中，定义消息对应的枚举  
3、如果消息对应的processor不在公共Core的包中，直接使用对应Processor的类名字符串即可。对应Processor可以定义在client或server包中，运行时会通过Spring对应查找到Processor的bean，完成spring环境的注入。  
4、参考： ClientRegisterRequest和ClientRegisterResponse  



客户端断线自动重连
---------------------------------------------
客户端网络断线之后，会在参数【netty.reconnectingSeconds】（默认10秒）后自动尝试连接服务器  


自动Ping
---------------------------------------------
客户端和服务器自动双向发起Ping  
两次Ping的间隔时间通过参数【netty.idleTimeSeconds】设定，默认为60秒  
双方自动判断在Ping间隔+10秒是否有数据到达，如果没有，判断为断线，关闭当前连接  


消息压缩和加密
---------------------------------------------
1、压缩： 已经使用Netty默认处理实现 
```
   .addLast("inflater", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP))
   .addLast("deflater", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP))
``` 

2、TODO 加密待调查和实现，需要注册登录的交互  




 


 