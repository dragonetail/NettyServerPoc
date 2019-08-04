package com.github.dragonetail.netty.poc.core;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterRequestMessage;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterResponseMessage;
import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import com.github.dragonetail.netty.poc.core.message.UnknownMessage;
import com.github.dragonetail.netty.poc.core.processor.HeartBeatProcessor;
import com.github.dragonetail.netty.poc.core.processor.UnknownProcessor;
import com.github.dragonetail.netty.poc.core.utils.SpringContextUtils;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;
import com.github.dragonetail.netty.poc.sample.procesor.SampleRequestProcessor;
import com.github.dragonetail.netty.poc.sample.procesor.SampleResponseProcessor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    unknown(-1, UnknownProcessor.class.getSimpleName(), UnknownMessage.class),
    heartBeat(0,  HeartBeatProcessor.class.getSimpleName(), HeartBeatMessage.class),
    clientRegisterRequest(1,  "ClientRegisterRequestProcessor", ClientRegisterRequestMessage.class),
    clientRegisterResponse(2,  "ClientRegisterResponseProcessor", ClientRegisterResponseMessage.class),
    sampleRequest(10,  SampleRequestProcessor.class.getSimpleName(), SampleRequestMessage.class),
    sampleResponse(11,  SampleResponseProcessor.class.getSimpleName(), SampleResponseMessage.class);

    public final int code;
    public final String processorName;
    public final Class<? extends BaseMessage> messageClass;

    Command(int code,
           String  processorName,
            Class<? extends BaseMessage> messageClass) {
        this.code = (byte) code;
        this.processorName = processorName;
        this.messageClass = messageClass;
    }

    static final Map<Integer, Command> enums = new HashMap<>();

    static {
        for (Command cmd : Command.values()) {
            enums.put(cmd.code, cmd);
        }
    }

    public static Command from(int code) {
        Command cmd = enums.get(code);

        return cmd == null ? unknown : cmd;
    }


    private final Map<String, BaseProcessor<BaseMessage>> processors = new HashMap<>();
    public BaseProcessor<BaseMessage> processor() {
        String beanName = StringUtils.uncapitalize(processorName);

        BaseProcessor<BaseMessage> processor = processors.get(beanName);
        if(processor != null){
            return processor;
        }

        processor = (BaseProcessor<BaseMessage>)SpringContextUtils.getBean(beanName);
        if(processor == null){
            throw new IllegalStateException("查找Processor失败: "+ beanName);
        }
        processors.put(beanName, processor);

        return processor;
    }

}