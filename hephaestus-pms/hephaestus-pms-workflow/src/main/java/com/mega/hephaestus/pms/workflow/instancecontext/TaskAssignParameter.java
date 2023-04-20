package com.mega.hephaestus.pms.workflow.instancecontext;


import lombok.Data;

/**
 * 用户操作参数
 */
@Data
@Deprecated(since = "20221115")
public class TaskAssignParameter {


    /**
     * assignParameter
     */
    private String assignParameter;
    /**
     * 显示标题
     */
    private String assignParameterTitle;
    /**
     * 参数值
     */
    private Object assignParameterValue;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 参数类型
     */
    private Integer type = 1;

    public TaskAssignParameter() {

    }

    public TaskAssignParameter(String assignParameter ,String assignParameterTitle, Object assignParameterValue, Integer sort) {
        this.assignParameter = assignParameter;
        this.assignParameterTitle = assignParameterTitle;
        this.assignParameterValue = assignParameterValue;
        this.sort = sort;
    }

}
