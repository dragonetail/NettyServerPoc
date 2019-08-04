package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.client.NettyClient;
import com.github.dragonetail.netty.poc.echo.message.EchoRequestMessage;
import com.github.dragonetail.netty.poc.echo.message.EchoResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    private NettyClient nettyClient;

    @RequestMapping("/")
    public String index() {
        return "Hi, Netty Client!";
    }

    @GetMapping("/client/sendEchoRequest")
    public String sendEchoRequest() {
        EchoRequestMessage message = new EchoRequestMessage();
        message.setContent("【Hi,2019.8.1 as EchoRequest from client "+ nettyClient.getClientId() + "】");
        nettyClient.send(message);
        return "sent ok";
    }

    @GetMapping("/client/sendEchoResponse")
    public String sendEchoResponse() {
        EchoResponseMessage message = new EchoResponseMessage();
        message.setContent("【Hi,2019.8.1 as EchoResponse from client "+ nettyClient.getClientId() + "】");
        nettyClient.send(message);
        return "sent ok";
    }

}