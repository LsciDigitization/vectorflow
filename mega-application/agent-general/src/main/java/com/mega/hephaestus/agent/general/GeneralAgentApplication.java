package com.mega.hephaestus.agent.general;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class
})
@EnableConfigurationProperties
@ComponentScan({
        "com.mega.hephaestus.pms.data",
        "com.mega.hephaestus.pms.workflow",
        "com.mega.hephaestus.pms.agent.dashboard",
})
@MapperScan(value = {
        "com.mega.hephaestus.pms.data.*.mapper",
        "com.mega.component.common.**.mapper"
})
@EnableScheduling
@Slf4j
public class GeneralAgentApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(GeneralAgentApplication.class, args);
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("GeneralAgentApplication main error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
