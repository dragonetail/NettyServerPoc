package com.github.dragonetail.netty.poc.client;


import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.handler.MessageDecoderHandler;
import com.github.dragonetail.netty.poc.core.handler.MessageEncoderHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NettyClient {
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Value("${netty.port}")
    private int port;
    @Value("${netty.host}")
    private String host;
    private Channel channel;

    public <T extends BaseMessage> void send(T message) {
            channel.writeAndFlush(message);
    }

    @PostConstruct
    public void start() {
        NettyClient nettyClient = this;
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
                                    .addLast(new IdleStateHandler(0, 30, 0))
                                    .addLast("frameDecode", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                            0, 2, 0, 2))
                                    .addLast("decoder", new MessageDecoderHandler())
                                    // 编码之前增加 两个字节的消息长度，
                                    .addLast("frame encoder", new LengthFieldPrepender(2))
                                    .addLast("encoder", new MessageEncoderHandler())
                                    .addLast(new ClientHeartbeatHandler())
                                    .addLast(new ClientHandler(nettyClient));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            channel = future.sync().channel();
                            log.info("连接Netty服务端成功");

                            channel.closeFuture().sync();
                        } else {
                            log.info("连接失败，进行断线重连");
                            future.channel().eventLoop().schedule(() -> start(), 20, TimeUnit.SECONDS);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        channel.close();
        workerGroup.shutdownGracefully();
        log.info("Netty Client已关闭。");
    }
}