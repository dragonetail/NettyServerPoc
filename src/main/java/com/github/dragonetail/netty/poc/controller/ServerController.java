package com.github.dragonetail.netty.poc.controller;

import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.server.ClientHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @Value("${netty.clientId}")
    private String clientId;
    @Autowired
    private ClientHolder clientHolder;

    @GetMapping("/server/send")
    public String send() {
        SampleRequestMessage message = new SampleRequestMessage();
        message.setContent("Hi,2019.8.1");
        clientHolder.send(clientId, message);
        return "send ok";
    }
}