package com.mega.hephaestus.pms.workflow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 23:52
 */
@Configuration(proxyBeanMethods = false)
public class WorkflowServiceFactoryConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public WorkflowServiceFactory workflowServiceFactory() {
        return new WorkflowServiceFactory(applicationContext);
    }

}
