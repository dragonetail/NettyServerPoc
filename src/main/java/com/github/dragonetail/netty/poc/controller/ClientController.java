package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.client.NettyClient;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/client")
public class ClientController {
    @Autowired
    private NettyClient nettyClient;

    @GetMapping("/send")
    public String send() {
        SampleRequestMessage message = new SampleRequestMessage("Hi,2019.8.1");
        nettyClient.send(message);
        return "send ok";
    }
}