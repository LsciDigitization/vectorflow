package com.mega.component.bioflow.task;

/**
 * @author wangzhengdong
 * @version 1.0
 */
public class ProjectId {

    private final String id;

    public ProjectId(String id) {
        this.id = id;
    }

    public ProjectId(Long id) {
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
