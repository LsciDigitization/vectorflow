package com.mega.hephaestus.pms.nuc.workflow.task;

public interface StorageTask {

    void put(String key, String hashKey, Object data);

    Object get(String key, String hashKey);

    void delete(String key, String... hasKey);

    boolean hasKey(String key, String... hasKey);


}
