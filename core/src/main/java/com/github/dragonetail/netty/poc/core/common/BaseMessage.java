package com.github.dragonetail.netty.poc.core.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
@Getter
@Setter
public class BaseMessage {
    private static final Charset utf8 = Charset.forName("utf-8");

    private Byte version = 1;

    @JsonIgnore
    public Command command(){
        return Command.unknown;
    }

    public final void process(ChannelHandlerContext ctx){
        Command command = command();
        command.processor().process(this, ctx);
    }

    @Override
    public String toString() {
        return "{" + "command=" + command() + "}";
    }

    public void pack(ByteBuf out) {
        String messageStr = JsonUtils.toJson(this);
        log.debug("messageStr: {}", messageStr);
        byte[] messageStrBytes = messageStr.getBytes(utf8);

        out.writeInt(command().code);
        out.writeBytes(messageStrBytes);
        //TODO  out.writeBytes(packetBytesCRC);
    }

    public static BaseMessage unpack(ByteBuf in) {
        int length = in.readInt();
        int commandCode = in.readInt();
        Command command = Command.from(commandCode);

        ByteBuf messageStrBytes = in.readBytes(length - 4);
        String messageStr = messageStrBytes.toString(utf8);
        //TODO  CRC verify

        BaseMessage message = JsonUtils.fromJson(messageStr, command.messageClass);
        return message;
    }
}

