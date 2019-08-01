package com.github.dragonetail.netty.poc.sample.message;


import com.github.dragonetail.netty.poc.core.common.BaseMessage;
import com.github.dragonetail.netty.poc.core.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SampleResponseMessage extends BaseMessage {
    private String content;

    public Command getCommand(){
        return Command.sampleResponse;
    }
}