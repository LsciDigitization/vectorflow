package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class TaskParameterSerializer {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 任务参数序列化方法
     * @param parameter 参数对象
     * @return 返回JSON字符串
     */
    public String serialize(Serializable parameter) {
        try {
            return objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 任务参数反序列化
     * @param json JSON字符串
     * @param clazz 转换出来的类
     * @return 返回指定类数据
     * @param <T> 任务参数类
     */
    public <T> T deserialize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 任务参数反序列化 Object
     * @param json JSON字符串
     * @return 返回指定类数据
     */
    public Object deserialize(String json) {
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
