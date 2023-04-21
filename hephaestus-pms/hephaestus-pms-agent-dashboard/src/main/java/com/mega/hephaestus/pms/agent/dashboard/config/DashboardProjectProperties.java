package com.mega.hephaestus.pms.agent.dashboard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 *  首页快捷菜单Properties
 */
@Data
@ConfigurationProperties(prefix = "dashboard.project")
@Configuration
public class DashboardProjectProperties {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目状态
     */
    private String projectStatus;


    /**
     * 项目描述
     */
    private String projectDescription;

    /**
     * 相关链接
     */
    private List<Link> links;

    @Data
    public static class Link{

        /**
         * 链接地址
         */
        private String url;

        /**
         * 链接名称
         */
        private String linkName;

    }
}
