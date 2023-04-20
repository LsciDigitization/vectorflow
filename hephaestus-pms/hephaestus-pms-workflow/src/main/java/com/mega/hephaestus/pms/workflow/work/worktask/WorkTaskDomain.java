package com.mega.hephaestus.pms.workflow.work.worktask;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkTaskDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    // 实验名称
    private String name;
    // 消息ID
    private String messageId;

    // 实验ID
    private Long experimentId;
    // 实验组ID
    private Long experimentGroupId;
    // 实验资源池ID
    private Long experimentPoolId;
    // 实验资源池类型
    private String experimentPoolType;
    // 实验板池ID
    private Long experimentPlateStorageId;

    // 板id
    private Long experimentPlateId;

    // 设备key
    private String deviceKey;
    // 实验组历史id
    private Long experimentGroupHistoryId;

}
