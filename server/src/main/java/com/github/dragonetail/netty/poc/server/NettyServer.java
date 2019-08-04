package com.github.dragonetail.netty.poc.server;


import com.github.dragonetail.netty.poc.core.handler.HeartbeatHandler;
import com.github.dragonetail.netty.poc.core.handler.MessageDecoderHandler;
import com.github.dragonetail.netty.poc.core.handler.MessageEncoderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class NettyServer {
    /**
     * boss 线程组用于处理连接工作
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * work 线程组用于数据处理
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Value("${netty.port}")
    private Integer port;
    @Value("${netty.idleTimeSeconds}")
    private int idleTimeSeconds;

    @Autowired
    private ServerHandler serverHandler;
    @Autowired
    private HeartbeatHandler heartbeatHandler;

    /**
     * 启动Netty Server
     */
    @PostConstruct
    public void startup() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 指定Channel
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    //new InetSocketAddress("127.0.0.1",port)
                    .localAddress(new InetSocketAddress(port))

                    //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG, 1024)

                    //设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true)

                    //将小的数据包包装成更大的帧进行传送，提高网络的负载
                    .childOption(ChannelOption.TCP_NODELAY, true)

                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    //空闲检测
                                    .addLast(new IdleStateHandler(idleTimeSeconds + 10, idleTimeSeconds, 0))
                                    .addLast("frameDecode", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                            0, 4, 0, 0))
                                    .addLast("decoder", new MessageDecoderHandler())
                                    // 编码之前增加 两个字节的消息长度，
                                    .addLast("frame encoder", new LengthFieldPrepender(4))
                                    .addLast("encoder", new MessageEncoderHandler())
                                    .addLast(heartbeatHandler)
                                    .addLast(serverHandler);
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync();
            if (future.isSuccess()) {
                log.info("服务器已启动。");
            }

            // 应用程序会一直等待，直到channel关闭
            //future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("服务器启动异常。", e);
            destroy();
        }
    }

    @PreDestroy
    public void destroy()  {
        try {
            bossGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
        }
        try {
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
        }
        log.info("服务器已退出。");
    }
}