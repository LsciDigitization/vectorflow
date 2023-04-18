package com.mega.component.json.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mega.component.json.JsonFacade;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

/**
 * Json配置
 */
public class JsonAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 序列化时，日期的统一格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // enum序列化为字符串
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        // 反序列化时，属性不存在的兼容处理
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }


    @Bean
    public JsonFacade jsonFacade() {
        return new JsonFacade();
    }

}
