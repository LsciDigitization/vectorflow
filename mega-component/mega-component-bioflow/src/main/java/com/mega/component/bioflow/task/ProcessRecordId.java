package com.mega.component.bioflow.task;

/**
 * @author wangzhengdong
 * @version 1.0
 */
public class ProcessRecordId {

    private final String id;

    public ProcessRecordId(String id) {
        this.id = id;
    }

    public ProcessRecordId(Long id) {
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
