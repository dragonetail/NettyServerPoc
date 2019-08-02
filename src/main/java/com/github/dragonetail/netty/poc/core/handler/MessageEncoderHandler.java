package com.github.dragonetail.netty.poc.core.handler;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class MessageEncoderHandler extends MessageToByteEncoder<BaseMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage message, ByteBuf out) {
        message.pack(out);
    }

}