package com.mega.hephaestus.pms.workflow.config.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/31 22:50
 */
@Slf4j
public abstract class BaseConfiguration<T> implements InitializingBean {

    protected final Map<String, T> typeCallableMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        registerServices();
        populatePool();
    }

    protected abstract void registerServices();

    protected abstract void populatePool();

}
