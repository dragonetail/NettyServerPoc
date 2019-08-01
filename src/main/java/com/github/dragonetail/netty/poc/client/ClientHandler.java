package com.github.dragonetail.netty.poc.client;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private NettyClient nettyClient;

    public ClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("成功连接服务器。");
    }

    /**
     * 收到消息后调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        log.info("得到消息，开始处理。");

        BaseMessage message = (BaseMessage)msg;
        message.process(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("已与服务器断开连接，正在尝试重新连接。");

        //如果运行过程中服务端挂了,执行重连机制
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> nettyClient.start(), 10L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("捕获的异常，与服务器断开连接：{}",cause.getMessage());
        ctx.close();
    }

}