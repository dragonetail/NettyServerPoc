package com.github.dragonetail.netty.poc.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ServerHeartbeatCheckHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("没有收到客户端的心跳，将断开连接。");
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, event);
        }
    }
}