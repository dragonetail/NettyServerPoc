Spring Boot Netty Poc
---------------------------------------------

Start Server and Testing
---------------------------------------------
Run NettyServerPocApplication.class

Testing:
```
curl localhost:8080
curl localhost:8080/server/sendSampleRequest
curl localhost:8080/server/sendSampleResponse
```

Start client and Testing
---------------------------------------------
Run NettyClientPocApplication.class

Testing:
```
curl localhost:8081
curl localhost:8081/client/sendSampleRequest
curl localhost:8081/client/sendSampleResponse
```