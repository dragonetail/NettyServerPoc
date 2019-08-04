package com.github.dragonetail.netty.poc.echo.message;


import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.Command;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EchoRequestMessage extends BaseMessage {
    private String content;

    public Command command(){
        return Command.echoRequest;
    }
}