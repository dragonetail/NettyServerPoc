package com.github.dragonetail.netty.poc.core.common;


import com.github.dragonetail.netty.poc.core.Command;
import com.github.dragonetail.netty.poc.core.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class BaseResponseMessage extends BaseMessage {
    private ResponseCode resCode;

}