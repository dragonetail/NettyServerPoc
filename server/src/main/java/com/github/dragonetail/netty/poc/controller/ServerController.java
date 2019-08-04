package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;
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

    @GetMapping("/server/sendSampleRequest")
    public String sendSampleRequest() {
        SampleRequestMessage message = new SampleRequestMessage();
        message.setContent("【Hi,2019.8.1 as SampleRequest from server to "+ clientId + "】");
        clientHolder.send(clientId,message);
        return "sent ok";
    }

    @GetMapping("/server/sendSampleResponse")
    public String sendSampleResponse() {
        SampleResponseMessage message = new SampleResponseMessage();
        message.setContent("【Hi,2019.8.1 as SampleResponse from client "+ clientId + "】");
        clientHolder.send(clientId, message);
        return "sent ok";
    }
}