package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.client.NettyClient;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
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
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/client/send")
    public String send() {
        SampleRequestMessage message = new SampleRequestMessage();
        message.setContent("Hi,2019.8.1");
        nettyClient.send(message);
        return "send ok";
    }
}