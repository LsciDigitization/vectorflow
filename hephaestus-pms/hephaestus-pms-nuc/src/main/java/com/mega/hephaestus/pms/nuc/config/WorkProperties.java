package com.mega.hephaestus.pms.nuc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hephaestus.work")
@Data
public class WorkProperties {

    // 是否启用工作线程
    private boolean enabled = false;
    // 任务时间率，最大100，默认100，最小1
    private int taskTimeRate = 100;

}
