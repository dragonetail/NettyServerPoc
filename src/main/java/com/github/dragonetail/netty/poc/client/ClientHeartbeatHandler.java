package com.github.dragonetail.netty.poc.client;

import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ClientHeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("已经30s没有发送消息给服务端");

                HeartBeatMessage message = new HeartBeatMessage();
                //发送心跳消息，并在发送失败时关闭该连接
                ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, event);
        }
    }
}