package com.mega.component.bioflow.task;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:40
 */
public class ExperimentId {
    private final String id;

    public ExperimentId(String id) {
        this.id = id;
    }

    public ExperimentId(Long id) {
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
