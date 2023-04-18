package com.mega.component.threadpool.config;

import com.mega.component.threadpool.endpoint.ThreadPoolEndpoint;
import com.mega.component.threadpool.ThreadPoolManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties
@Import({
        NewThreadPoolConfig.class,
})
public class ThreadPoolAutoConfiguration {

    @Bean
    public ThreadPoolManager getBean() {
        return new ThreadPoolManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolEndpoint getThreadPoolEndpoint() {
        return new ThreadPoolEndpoint();
    }

}
