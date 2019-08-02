package com.github.dragonetail.netty.poc.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> String toJson(T object)  {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON失败。", e);
            throw new IllegalStateException("对象转JSON失败。", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("JSON转对象失败。", e);
            throw new IllegalStateException("JSON转对象失败。", e);
        }
    }

//    public static Packet<?> fromJson(String json, TypeReference<?> typeReference) {
//        try {
//            return mapper.readValue(json, typeReference);
//        } catch (IOException e) {
//            log.error("JSON转对象失败。", e);
//            throw new IllegalStateException("JSON转对象失败。", e);
//        }
//    }
}
