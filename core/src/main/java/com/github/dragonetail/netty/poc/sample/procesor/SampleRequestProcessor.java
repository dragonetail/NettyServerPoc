package com.github.dragonetail.netty.poc.sample.procesor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class SampleRequestProcessor extends BaseProcessor<SampleRequestMessage> {

    @Override
    public void process(SampleRequestMessage message, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("收到对方的({})请求包: {}", channel.remoteAddress(), message.getContent());

        SampleResponseMessage responseMessage = new SampleResponseMessage();
        responseMessage.setContent("【Echo】" + message.getContent());
        ctx.writeAndFlush(responseMessage);
    }
}