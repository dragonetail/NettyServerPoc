package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.echo.message.EchoRequestMessage;
import com.github.dragonetail.netty.poc.echo.message.EchoResponseMessage;
import com.github.dragonetail.netty.poc.server.ClientHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @Value("${netty.clientId}")
    private String clientId;
    @Autowired
    private ClientHolder clientHolder;

    @RequestMapping("/")
    public String index() {
        return "Hi, Netty Server!";
    }

    @GetMapping("/server/sendEchoRequest")
    public String sendEchoRequest() {
        EchoRequestMessage message = new EchoRequestMessage();
        message.setContent("【Hi,2019.8.1 as EchoRequest from server to "+ clientId + "】");
        clientHolder.send(clientId,message);
        return "sent ok";
    }

    @GetMapping("/server/sendEchoResponse")
    public String sendEchoResponse() {
        EchoResponseMessage message = new EchoResponseMessage();
        message.setContent("【Hi,2019.8.1 as EchoResponse from client "+ clientId + "】");
        clientHolder.send(clientId, message);
        return "sent ok";
    }
}