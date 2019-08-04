package com.github.dragonetail.netty.poc.processor;

import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ClientRegisterResponseProcessor extends BaseProcessor<ClientRegisterResponseMessage> {

    @Override
    public void process(ClientRegisterResponseMessage message, ChannelHandlerContext ctx) {
        log.info("收到服务器返回的注册响应包: {}",  message.getResCode());
    }
}