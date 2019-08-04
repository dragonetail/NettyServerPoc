package com.github.dragonetail.netty.poc.echo.procesor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.echo.message.EchoRequestMessage;
import com.github.dragonetail.netty.poc.echo.message.EchoResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class EchoRequestProcessor extends BaseProcessor<EchoRequestMessage> {

    @Override
    public void process(EchoRequestMessage message, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("收到对方的({})请求包: {}", channel.remoteAddress(), message.getContent());

        EchoResponseMessage responseMessage = new EchoResponseMessage();
        responseMessage.setContent("【Echo】" + message.getContent());
        ctx.writeAndFlush(responseMessage);
    }
}