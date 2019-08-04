package com.github.dragonetail.netty.poc.core;

import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.common.BaseProcessor;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterRequestMessage;
import com.github.dragonetail.netty.poc.core.message.ClientRegisterResponseMessage;
import com.github.dragonetail.netty.poc.core.message.HeartBeatMessage;
import com.github.dragonetail.netty.poc.core.message.UnknownMessage;
import com.github.dragonetail.netty.poc.core.processor.ClientRegisterRequestHandler;
import com.github.dragonetail.netty.poc.core.processor.ClientRegisterResponseHandler;
import com.github.dragonetail.netty.poc.core.processor.HeartBeatHandler;
import com.github.dragonetail.netty.poc.core.processor.UnknownHandler;
import com.github.dragonetail.netty.poc.core.utils.SpringContextUtils;
import com.github.dragonetail.netty.poc.sample.message.SampleRequestMessage;
import com.github.dragonetail.netty.poc.sample.message.SampleResponseMessage;
import com.github.dragonetail.netty.poc.sample.procesor.SampleRequestHandler;
import com.github.dragonetail.netty.poc.sample.procesor.SampleResponseHandler;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    unknown(-1, UnknownHandler.class.getSimpleName(), UnknownMessage.class),
    heartBeat(0,  HeartBeatHandler.class.getSimpleName(), HeartBeatMessage.class),
    clientRegisterRequest(1,  ClientRegisterRequestHandler.class.getSimpleName(), ClientRegisterRequestMessage.class),
    clientRegisterResponse(2,  ClientRegisterResponseHandler.class.getSimpleName(), ClientRegisterResponseMessage.class),
    sampleRequest(10,  SampleRequestHandler.class.getSimpleName(), SampleRequestMessage.class),
    sampleResponse(11,  SampleResponseHandler.class.getSimpleName(), SampleResponseMessage.class);
    //TODO processor更改成Spring的Bean的Name，由Spring容器中查找


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