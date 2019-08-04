package com.github.dragonetail.netty.poc.core.processor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class HeartBeatHandler extends BaseProcessor<HeartBeatMessage> {

    @Override
    public void process(HeartBeatMessage message, ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        log.info("收到客户端({})心跳包。", incoming.remoteAddress());
    }
}