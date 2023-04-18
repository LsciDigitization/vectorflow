package com.mega.component.workflow.starter.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "zookeeper.curator")
public class ZookeeperProperties {

    private boolean enabled;

    // 集群地址
    private String host;

    // 连接超时时间
    private Integer connectionTimeoutMs;

    // 会话超时时间
    private Integer sessionTimeout;

    // 重试机制时间参数
    private Integer sleepMsBetweenRetry;

    // 重度机制重试次数
    private Integer maxRetries;

    // 命令空间（父节点名称）
    private String namespace;

    public boolean isEnabled() {
        return enabled;
    }

    public ZookeeperProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getHost() {
        return host;
    }

    public ZookeeperProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public ZookeeperProperties setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
        return this;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public ZookeeperProperties setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public Integer getSleepMsBetweenRetry() {
        return sleepMsBetweenRetry;
    }

    public ZookeeperProperties setSleepMsBetweenRetry(Integer sleepMsBetweenRetry) {
        this.sleepMsBetweenRetry = sleepMsBetweenRetry;
        return this;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public ZookeeperProperties setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public ZookeeperProperties setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

}
