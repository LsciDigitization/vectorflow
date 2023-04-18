package com.mega.component.bioflow.task;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 17:13
 */
public class InstanceId {

    private final String id;

    public InstanceId(String id) {
        this.id = id;
    }

    public InstanceId(Long id) {
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }

    public long getLongId() {
        return Long.parseLong(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
