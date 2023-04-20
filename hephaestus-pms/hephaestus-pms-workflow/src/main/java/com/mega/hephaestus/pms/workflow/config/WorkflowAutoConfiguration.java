package com.mega.hephaestus.pms.workflow.config;

import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.task.stagetask.TaskParameterSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        ExperimentProperties.class,
        TaskParameterSerializer.class
})
@EnableConfigurationProperties
@Configuration(proxyBeanMethods = true)
public class WorkflowAutoConfiguration {

}
