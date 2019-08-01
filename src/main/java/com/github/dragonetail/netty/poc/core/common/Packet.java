package com.github.dragonetail.netty.poc.core.common;

import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;


@Data
public class Packet<T extends BaseMessage> {
    private static final Charset utf8 = Charset.forName("utf-8");

    private Byte version = 1;

    private Command command;

    //消息体
    private T payload;

    @Override
    public String toString() {
        return "{" + "command=" + command + "}";
    }

    public void pack(ByteBuf out) {
        String packetStr = JsonUtils.toJson(this);
        byte[] packetBytes = packetStr.getBytes(utf8);

        out.writeInt(command.code);
        out.writeBytes(packetBytes);
        //TODO  out.writeBytes(packetBytesCRC);
    }

    public static Packet<?> unpack(ByteBuf in)  {
        int length = in.readInt();
        int commandCode = in.readInt();
        Command command = Command.from(commandCode);

        ByteBuf byteBuf = in.readBytes(length - 4);
        String packetStr = byteBuf.toString(utf8);
        //TODO  CRC verify

        Packet<?> packet = JsonUtils.fromJson(packetStr, command.typeReference);
        return packet;
    }
}