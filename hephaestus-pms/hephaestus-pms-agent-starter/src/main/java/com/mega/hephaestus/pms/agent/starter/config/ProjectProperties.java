package com.mega.hephaestus.pms.agent.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/15 17:01
 */
@Data
@ConfigurationProperties(prefix = "hephaestus.agent.project")
public class ProjectProperties {

    /**
     * 项目代号
     */
    private String code;

    /**
     * 实验组Id
     */
    private Long experimentGroupId;

    /**
     * 是否自动启动
     */
    private boolean autoStart = false;

}
