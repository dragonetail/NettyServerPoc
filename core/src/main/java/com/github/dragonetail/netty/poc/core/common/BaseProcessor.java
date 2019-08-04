package com.github.dragonetail.netty.poc.core.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseProcessor<T extends BaseMessage> {

    public void process(T payload, ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        log.info("收到客户端({})的数据包： {}", channel.remoteAddress(), payload);
    }
}