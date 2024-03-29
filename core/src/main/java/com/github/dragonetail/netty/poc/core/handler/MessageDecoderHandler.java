package com.github.dragonetail.netty.poc.core.handler;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

@Sharable
public class MessageDecoderHandler extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        BaseMessage message = BaseMessage.unpack(in);
        out.add(message);
    }
}