package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.client.NettyClient;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;
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

    @GetMapping("/client/sendSampleRequest")
    public String sendSampleRequest() {
        SampleRequestMessage message = new SampleRequestMessage();
        message.setContent("【Hi,2019.8.1 as SampleRequest from client "+ nettyClient.getClientId() + "】");
        nettyClient.send(message);
        return "sent ok";
    }

    @GetMapping("/client/sendSampleResponse")
    public String sendSampleResponse() {
        SampleResponseMessage message = new SampleResponseMessage();
        message.setContent("【Hi,2019.8.1 as SampleResponse from client "+ nettyClient.getClientId() + "】");
        nettyClient.send(message);
        return "sent ok";
    }

}