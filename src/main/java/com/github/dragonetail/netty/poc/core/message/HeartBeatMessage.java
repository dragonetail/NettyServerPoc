package com.github.dragonetail.netty.poc.core.message;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.Command;

public class HeartBeatMessage extends BaseMessage {

    public Command getCommand(){
        return Command.heartBeat;
    }

}