package com.mega.component.threadpool.config;

import com.mega.component.threadpool.queue.ResizeableBlockingQueue;
import com.mega.component.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
public class NewThreadPoolConfig {
    @Autowired
    private ThreadPoolManager threadPoolUtil;

    @Bean
    public ThreadPoolExecutor asyncExecutor() {
        return threadPoolUtil.creatThreadPool(
                16,
                32,
                60,
                TimeUnit.SECONDS,
                new ResizeableBlockingQueue<>(500),
                "asyncExecutor"
        );
    }


    @Bean
    public ThreadPoolExecutor workflowExecutor() {
        return threadPoolUtil.creatThreadPool(
                128,
                128,
                60,
                TimeUnit.SECONDS,
                new ResizeableBlockingQueue<>(256),
                "Workflow"
        );
    }

    @Bean
    public ThreadPoolExecutor deviceTaskExecutor() {
        return threadPoolUtil.creatThreadPool(
                8,
                16,
                60,
                TimeUnit.SECONDS,
                new ResizeableBlockingQueue<>(50),
                "DeviceTask"
        );
    }


    @Bean
    public ThreadPoolExecutor deviceLockExecutor() {
        return threadPoolUtil.creatThreadPool(
                8,
                8,
                60,
                TimeUnit.SECONDS,
                new ResizeableBlockingQueue<>(10),
                "DeviceLock"
        );
    }

}
