package com.github.dragonetail.netty.poc.echo.procesor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.echo.message.EchoResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class EchoResponseProcessor extends BaseProcessor<EchoResponseMessage> {

    @Override
    public void process(EchoResponseMessage message, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("收到对方的({})响应包: {}", channel.remoteAddress(), message.getContent());
    }
}