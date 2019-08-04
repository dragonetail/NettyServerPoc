package com.github.dragonetail.netty.poc.server;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ClientHolder {
    private Map<String, Channel> clients = new HashMap<>();

    //TODO 扩展其他信息，例如客户端IP地址，客户端其他信息等。。。

    public void register(String clientId, Channel channel) {
        clients.put(clientId, channel);
    }

    public boolean exist(String clientId) {
        return clients.containsKey(clientId);
    }

    public void remove(Channel channel) {
        clients.remove(channel);
    }

    public <T extends BaseMessage> void send(String clientId, T message) {
        Channel channel = clients.get(clientId);
        if (channel == null) {
            throw new IllegalStateException("向客户(" + clientId +")发送消息时客户已下线: " + message);
        }

        channel.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

}