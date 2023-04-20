package com.mega.hephaestus.pms.workflow.manager.plan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import com.mega.hephaestus.pms.workflow.task.taskbuild.Task;

import java.util.*;

/**
 * 实验阶段操作
 */
@Deprecated(since = "20230323")
public interface ExperimentStageManager {

    /**
     * 获取一个实验的所有stages
     * @param experimentId 实验ID
     * @return HephaestusStage list
     */
    List<HephaestusStage> getStages(Long experimentId);

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experimentId 实验ID
     * @return Task.RootTask
     */
    Task.RootTask getStageRootTask(Long experimentId);

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experimentId 实验ID
     * @return Json字符串
     * @throws JsonProcessingException JSON异常
     */
    String getStageRootTaskAsJson(Long experimentId) throws JsonProcessingException;

    String getStageRootTaskAsJson(Long experimentId, SpecialMeta specialMeta) throws JsonProcessingException;


    Map<String, HephaestusStage> getStageToMap(List<HephaestusStage> hephaestusStages);

    List<Task> buildStageToTask(List<HephaestusStage> hephaestusStages);

    Task.RootTask buildStageRootTask(List<Task> tasks);

    String toJson(Task.RootTask rootTask) throws JsonProcessingException;

}
