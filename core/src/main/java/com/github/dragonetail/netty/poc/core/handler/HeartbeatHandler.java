package com.github.dragonetail.netty.poc.core.handler;

import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Sharable
@Component
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            Channel channel = ctx.channel();
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                HeartBeatMessage message = new HeartBeatMessage();

                //发送心跳消息，并在发送失败时关闭该连接
                ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                log.info("已向对方发送心跳包【{} -. {}】。", channel.localAddress(), channel.remoteAddress());
            } else if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("没有收到对方【{}】的心跳，将断开连接。",channel.remoteAddress());
                channel.close();
            }
            super.userEventTriggered(ctx, event);
        } else {
            super.userEventTriggered(ctx, event);
        }
    }
}