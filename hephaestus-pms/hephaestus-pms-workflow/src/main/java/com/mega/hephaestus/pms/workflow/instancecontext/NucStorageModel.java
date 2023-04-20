package com.mega.hephaestus.pms.workflow.instancecontext;

import lombok.Data;
import lombok.ToString;

/**
 * 存储位置模型
 */
@Data
@ToString
@Deprecated(since = "20221115")
public class NucStorageModel {

    public NucStorageModel(){
    }
    public NucStorageModel(String deviceId,String nestGroupId,String gripOrientation,String nest){
        this.deviceId = deviceId;
        this.nest = nest;
        this.gripOrientation = gripOrientation;
        this.nestGroupId = nestGroupId;
    }
    /**
     * NucId
     */
    private String deviceId;
    /**
     * nestGroupId
     */
    private String nestGroupId;
    /**
     * 抓取方式
     */
    private String gripOrientation;
    /**
     * 位置Id
     */
    private String nest;
}
