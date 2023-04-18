package com.mega.component.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

final public class JsonFacade {

    @Resource
    private ObjectMapper objectMapper;

    public Optional<Map<String, Object>> toMap(String json) {
        if (Objects.isNull(json)) {
            return Optional.empty();
        }

        try {
            Map<String, Object> parameterMap = objectMapper.readValue(json, new TypeReference<>() {});
            return Optional.of(parameterMap);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public <T> Optional<T> toObject(String json, Class<T> tClass) {
        if (Objects.isNull(json)) {
            return Optional.empty();
        }

        T parameter;
        try {
            parameter = objectMapper.readValue(json, tClass);
            return Optional.of(parameter);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public <T> Optional<T> toObject(String json, TypeReference<T> valueTypeRef) {
        if (Objects.isNull(json)) {
            return Optional.empty();
        }

        T parameter;
        try {
            parameter = objectMapper.readValue(json, valueTypeRef);
            return Optional.of(parameter);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }


    public Optional<String> toJsonString(Map<String, Object> map) {
        if (Objects.isNull(map)) {
            return Optional.empty();
        }

        try {
            String s = objectMapper.writeValueAsString(map);
            return Optional.of(s);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> toJsonString(Object map) {
        if (Objects.isNull(map)) {
            return Optional.empty();
        }

        try {
            String s = objectMapper.writeValueAsString(map);
            return Optional.of(s);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
    

}
