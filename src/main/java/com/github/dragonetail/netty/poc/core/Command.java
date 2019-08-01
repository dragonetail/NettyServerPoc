package com.github.dragonetail.netty.poc.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.common.Packet;
import com.github.dragonetail.netty.poc.core.processor.HeartBeatHandler;
import com.github.dragonetail.netty.poc.sample.procesor.SampleRequestHandler;
import com.github.dragonetail.netty.poc.sample.procesor.SampleResponseHandler;
import com.github.dragonetail.netty.poc.core.processor.UnknownHandler;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    unknown(-1, new UnknownHandler(), new TypeReference<Packet<BaseMessage>>() {}),
    heartBeat(0, new HeartBeatHandler(), new TypeReference<Packet<BaseMessage>>() {}),
    sampleRequest(10, new SampleRequestHandler(), new TypeReference<Packet<SampleRequestMessage>>() {}),
    sampleResponse(11, new SampleResponseHandler(), new TypeReference<Packet<SampleResponseMessage>>() {});
    //TODO processor更改成Spring的Bean的Name，由Spring容器中查找


    public final int code;
    public final BaseProcessor<BaseMessage> processor;
    public final TypeReference<?> typeReference;

    Command(int code,
            BaseProcessor<?> processor,
            TypeReference<?> typeReference) {
        this.code = (byte) code;
        this.processor = (BaseProcessor<BaseMessage>)processor;
        this.typeReference = typeReference;
    }

    static final Map<Integer, Command> enums = new HashMap<>();
    static {
        for(Command cmd: Command.values()){
            enums.put(cmd.code, cmd);
        }
    }

    public static Command from(int code) {
        Command cmd = enums.get(code);

        return cmd == null ? unknown : cmd;
    }
}