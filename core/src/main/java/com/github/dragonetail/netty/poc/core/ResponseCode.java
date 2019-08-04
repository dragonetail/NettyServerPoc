package com.github.dragonetail.netty.poc.core;

public enum ResponseCode {
    unknownError(-1),
    ok(0);


    public final int code;

    ResponseCode(int code) {
        this.code = (byte) code;
    }
}