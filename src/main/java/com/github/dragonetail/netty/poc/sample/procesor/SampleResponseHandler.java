package com.github.dragonetail.netty.poc.sample.procesor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SampleResponseHandler extends BaseProcessor<SampleRequestMessage> {

    @Override
    public void process(SampleRequestMessage message, ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        log.info("收到对方的({})响应包。", incoming.remoteAddress(), message);
    }
}