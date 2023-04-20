package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.google.common.collect.Maps;

import java.util.Map;

public class SpecialMeta {

    private final Map<String, String> metaData;

    public SpecialMeta() {
        metaData = Maps.newHashMap();
    }

    public SpecialMeta(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public void putMetaData(SpecialMetaKeyEnum keyEnum, String value) {
        metaData.put(keyEnum.name(), value);
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public String getMetaData(SpecialMetaKeyEnum keyEnum) {
        if (metaData.containsKey(keyEnum.name())) {
            return metaData.get(keyEnum.name());
        }
        return null;
    }

    public enum SpecialMetaKeyEnum {
        EXPERIMENT_INSTANCE_ID,
        ;
    }


}
