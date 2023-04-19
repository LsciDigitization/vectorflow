package com.mega.hephaestus.pms.nuc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "hephaestus.nuc")
@Data
public class DeviceProperties {
    // 设备配置注册：file, database
    private String registryType = "file";
    // 是否使用代理地址
    private boolean useProxy = false;
    // 是否不使用设备请求
    private boolean debug = false;

    private Map<String, DeviceConfiguration> device;

}
