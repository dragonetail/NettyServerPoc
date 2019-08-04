package com.github.dragonetail.netty.poc.client;


import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.handler.HeartbeatHandler;
import com.github.dragonetail.netty.poc.core.handler.MessageDecoderHandler;
import com.github.dragonetail.netty.poc.core.handler.MessageEncoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NettyClient {
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Getter
    @Value("${netty.clientId}")
    private String clientId;
    @Value("${netty.port}")
    private int port;
    @Value("${netty.host}")
    private String host;
    @Value("${netty.idleTimeSeconds}")
    private int idleTimeSeconds;

    private Channel channel;

    @Autowired
    private ClientHandler clientHandler;
    @Autowired
    private HeartbeatHandler heartbeatHandler;

    public <T extends BaseMessage> void send(T message) {
        channel.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    @PostConstruct
    public void connect() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(idleTimeSeconds + 10, idleTimeSeconds, 0))
                                    .addLast("frameDecode", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                            0, 4, 0, 0))
                                    .addLast("decoder", new MessageDecoderHandler())
                                    // 编码之前增加 两个字节的消息长度，
                                    .addLast("frame encoder", new LengthFieldPrepender(4))
                                    .addLast("encoder", new MessageEncoderHandler())
                                    .addLast(heartbeatHandler)
                                    .addLast(clientHandler);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            channel = future.sync().channel();
                            log.info("连接服务端成功。");
                        } else {
                            log.info("连接失败，进行断线重连。");
                            future.channel().eventLoop().schedule(() -> connect(), 10, TimeUnit.SECONDS);
                        }
                    }
            );
        } catch (Exception e) {
            log.error("发起向服务器的连接失败。", e);
        }
    }

    @PreDestroy
    public void destroy() {
        channel.close();
        try {
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
        }
        log.info("客户端已退出。");
    }
}