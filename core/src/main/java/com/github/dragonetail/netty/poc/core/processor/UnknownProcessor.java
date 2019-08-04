package com.github.dragonetail.netty.poc.core.processor;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class UnknownProcessor extends BaseProcessor<BaseMessage> {

    @Override
    public void process(BaseMessage message, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.warn("收到对方({})不可识别的数据包，将断开连接。", channel.remoteAddress());

        ctx.close();
    }
}