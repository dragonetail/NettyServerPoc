package com.github.dragonetail.netty.poc.core.message;


import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.common.BaseResponseMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRegisterResponseMessage extends BaseResponseMessage {

    public Command command(){
        return Command.clientRegisterResponse;
    }
}