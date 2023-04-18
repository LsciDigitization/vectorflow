package com.mega.component.bioflow.task;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:39
 */
@Data
public class StageEntity {

    /**
     * 阶段ID
     */
    private StageId id;

    /**
     * stage名称
     */
    private String stageName;

    /**
     * stage描述
     */
    private String stageDescription;

    /**
     * 实验ID，所属实验
     */
    private ExperimentId experimentId;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 优先等级 默认50
     */
    private Integer priorityLevel = 50;

    /**
     * stage类型  开始、执行、等待、判断、结束
     */
    private StageType stageType;

    /**
     * 是否允许跳过 默认 否
     */
    private Boolean isSkip = false;

    /**
     * 运行前任务
     */
    private List<StageTaskEntity> beforeTasks = new ArrayList<>();

    /**
     * 运行任务
     */
    private List<StageTaskEntity> tasks = new ArrayList<>();

    /**
     * 运行后任务
     */
    private List<StageTaskEntity> afterTasks = new ArrayList<>();


}
