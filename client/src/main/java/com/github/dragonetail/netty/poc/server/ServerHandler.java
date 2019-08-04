package com.github.dragonetail.netty.poc.server;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    //TODO 管理所有接入的Channels，以便服务器端下发指令
//    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
//        channels.add(ctx.channel());
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
//        channels.remove(ctx.channel());
//    }


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
        Channel incoming = ctx.channel();
        log.info("客户端{}上线。" , incoming.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) { // (6)
        Channel incoming = ctx.channel();
        log.info("客户端{}掉线。" , incoming.remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        log.warn("服务器{}处理异常，将中断连接。" , incoming.remoteAddress(), cause);

        // 当出现异常就关闭连接
        ctx.close();
    }
}