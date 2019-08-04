package com.github.dragonetail.netty.poc.processor;

import com.github.dragonetail.netty.poc.core.ResponseCode;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterRequestMessage;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterResponseMessage;
import com.github.dragonetail.netty.poc.server.ClientHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ClientRegisterRequestProcessor extends BaseProcessor<ClientRegisterRequestMessage> {
    @Autowired
    private ClientHolder clientHolder;

    @Override
    public void process(ClientRegisterRequestMessage message, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("收到客户端({})发送过来的注册请求包: {}", channel.remoteAddress(), message.getClientId());

        ClientRegisterResponseMessage response = new ClientRegisterResponseMessage();
        response.setResCode(ResponseCode.ok);
        ctx.writeAndFlush(response);

        //保存客户端连接
        clientHolder.register(message.getClientId(), channel);
    }
}