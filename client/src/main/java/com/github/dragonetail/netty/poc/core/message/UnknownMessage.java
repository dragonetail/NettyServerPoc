package com.github.dragonetail.netty.poc.core.message;

import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.common.BaseMessage;

public class UnknownMessage extends BaseMessage {

    public Command command(){
        return Command.unknown;
    }

}