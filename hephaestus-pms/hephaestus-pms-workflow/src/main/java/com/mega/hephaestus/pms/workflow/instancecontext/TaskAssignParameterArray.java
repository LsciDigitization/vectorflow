package com.mega.hephaestus.pms.workflow.instancecontext;


import lombok.Data;

import java.util.List;

/**
 * 用户操作参数
 */
@Data
@Deprecated(since = "20221115")
public class TaskAssignParameterArray {

    List<TaskAssignParameter> data;
}
