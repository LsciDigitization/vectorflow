package com.mega.hephaestus.pms.workflow.instancecontext;

import lombok.Data;

@Data
@Deprecated(since = "20230210")
public class ExperimentInstanceContext {

    // 启动板位ID
    private Long startStorageId;
    // 结束板位ID
    private Long endStorageId;

    // 启动板位key
    private String startStorageKey;
    // 启动板位类型
    private String startPoolType;
    // 启动资源池ID
    private Long startPoolId;

}
