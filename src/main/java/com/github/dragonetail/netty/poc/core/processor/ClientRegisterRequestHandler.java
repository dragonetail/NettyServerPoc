package com.github.dragonetail.netty.poc.core.processor;

import com.github.dragonetail.netty.poc.core.ResponseCode;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterRequestMessage;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ClientRegisterRequestHandler extends BaseProcessor<ClientRegisterRequestMessage> {

    @Override
    public void process(ClientRegisterRequestMessage message, ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        log.info("收到客户端({})发送过来的注册请求包: {}", incoming.remoteAddress(), message.getClientId());

        ClientRegisterResponseMessage response = new ClientRegisterResponseMessage();
        response.setResCode(ResponseCode.ok);
        ctx.writeAndFlush(response);
    }
}