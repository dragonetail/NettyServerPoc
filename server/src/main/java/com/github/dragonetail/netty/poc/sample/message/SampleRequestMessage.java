package com.github.dragonetail.netty.poc.sample.message;


import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.Command;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SampleRequestMessage extends BaseMessage {
    private String content;

    public Command command(){
        return Command.sampleRequest;
    }
}