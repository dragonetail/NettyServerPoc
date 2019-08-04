package com.github.dragonetail.netty.poc.client;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterRequestMessage;
import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Sharable
@Component
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private NettyClient nettyClient;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("成功连接服务器。");

        HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
        ctx.channel().writeAndFlush(heartBeatMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        log.info("已向服务器发送初始心跳包。");

        ClientRegisterRequestMessage registerRequest = new ClientRegisterRequestMessage();
        registerRequest.setClientId(nettyClient.getClientId());
        ctx.channel().writeAndFlush(registerRequest).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        log.info("已向服务器发送初始注册包。");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(log.isDebugEnabled()) {
            log.debug("客户端收到服务器消息: {}" , msg);
        }

        BaseMessage message = (BaseMessage) msg;
        message.process(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("与服务器连接已断开，将尝试重新连接。");

        //如果运行过程中服务端挂了,执行重连机制
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> nettyClient.connect(), 10L, TimeUnit.SECONDS);

        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        Channel channel = ctx.channel();
        log.warn("客户端{}处理异常，将中断连接。" , channel.remoteAddress(), cause);

        // 当出现异常就关闭连接
        ctx.close();
    }

}