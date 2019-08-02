package com.github.dragonetail.netty.poc.core.message;


import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class ClientRegisterRequestMessage extends BaseMessage {
    private String clientId;

    //TODO 其他参数

    public Command command(){
        return Command.clientRegisterRequest;
    }
}