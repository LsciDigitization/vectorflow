package com.mega.hephaestus.pms.agent.starter.config;

import com.mega.hephaestus.pms.agent.starter.runner.ExperimentGroupStart;
import org.springframework.context.annotation.Import;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/15 17:05
 */
@Import({
        ProjectProperties.class,
        DataSourceInitializationConfiguration.class,
        ExperimentGroupStart.class,
})
public class AgentAutoConfiguration {
}
