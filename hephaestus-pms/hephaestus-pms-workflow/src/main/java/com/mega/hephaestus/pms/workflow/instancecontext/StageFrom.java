package com.mega.hephaestus.pms.workflow.instancecontext;


import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用户操作参数
 */
@Data
@Deprecated(since = "20221115")
public class StageFrom {

    /**
     * 参数名称
     */
    private String StageName;
    private Long StageId;
    private Map<String, List<TaskAssignParameter>> taskParameterMap;

}
