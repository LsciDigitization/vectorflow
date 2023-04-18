package com.mega.component.bioflow.task;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:38
 */
public class StageTaskId {
    private final String id;

    public StageTaskId(String id) {
        this.id = id;
    }

    public StageTaskId(Long id) {
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
