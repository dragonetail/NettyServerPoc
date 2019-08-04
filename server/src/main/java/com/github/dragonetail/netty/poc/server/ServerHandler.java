package com.github.dragonetail.netty.poc.server;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Sharable
@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private ClientHolder clientHolder;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(log.isDebugEnabled()) {
            log.debug("服务器收到客户端消息: {}" , msg);
        }

        BaseMessage message = (BaseMessage)msg;
        message.process(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) { // (5)
        Channel channel = ctx.channel();
        log.info("客户端{}上线。" , channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) { // (6)
        Channel channel = ctx.channel();
        log.info("客户端{}掉线。" , channel.remoteAddress());

        //删除客户端连接
        clientHolder.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel channel = ctx.channel();
        log.warn("服务器{}处理异常，将中断连接。" , channel.remoteAddress(), cause);

        // 当出现异常就关闭连接
        ctx.close();
    }
}