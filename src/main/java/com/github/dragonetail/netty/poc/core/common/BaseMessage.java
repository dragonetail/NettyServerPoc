package com.github.dragonetail.netty.poc.core.common;

import com.github.dragonetail.netty.poc.core.Command;
import io.netty.channel.ChannelHandlerContext;

public class BaseMessage {

    public Command getCommand(){
        return Command.unknown;
    }

    public final void process(ChannelHandlerContext ctx){
        Command command = getCommand();
        command.processor.process(this, ctx);
    }

    public final <T extends BaseMessage> Packet<T> buildPacket() {
        Packet<T> packet = new Packet<>();
        packet.setCommand(getCommand());
        packet.setPayload((T) this);
        return packet;
    }

}