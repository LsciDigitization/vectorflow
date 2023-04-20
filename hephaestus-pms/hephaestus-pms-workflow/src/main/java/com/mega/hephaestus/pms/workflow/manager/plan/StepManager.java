package com.mega.hephaestus.pms.workflow.manager.plan;


import com.mega.component.bioflow.step.Experiment;
import com.mega.component.bioflow.step.ExperimentStep;
import com.mega.component.bioflow.step.PlateLine;
import com.mega.component.bioflow.task.ProcessId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.workflow.manager.model.StepModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StepManager {

    /**
     * 根据项目拿到 step
     *  SELECT * FROM hephaestus_step_condition WHERE (project_id = ? AND is_deleted = ?)
     *  Parameters: 1613093052115607555(Long), 0(Integer)
     * @param projectId 项目id
     * @return List<StepModel>
     */
    List<StepModel> listByProjectId(ProjectId projectId);


    /**
     * 根据stepKey 拿到step
     *  SELECT * FROM hephaestus_step_condition WHERE (project_id = ? AND step_key = ? AND is_deleted = ?)
     *   1613093052115607555(Long), step1(String), 0(Integer)
     *
     *   SELECT * FROM hephaestus_step_condition WHERE (project_id = ? AND step_key = ? AND is_deleted = ?)
     *   1613093052115607555(Long), step1(String), 0(Integer)
     * @param projectId 项目id
     * @param stepType 步骤key
     * @return Optional<StepModel>
     */
    Optional<StepModel> getByStepKey(ProjectId projectId, StepType stepType);

    /**
     * 拿到第一个step 获取sortOrder最小的
     *  1613093052115607555(Long), 0(Integer)
     *  SELECT * FROM hephaestus_step_condition WHERE (project_id = ? AND is_deleted = ?)
     * @param projectId 项目
     * @return 第一个step
     */
    Optional<StepModel> getFirstStep(ProjectId projectId);


    List<ExperimentStep> listByProjectIdAndProcessId(ProjectId projectId, ProcessId processId);

    Experiment getExperiment(ProjectId projectId, ProcessId processId);


    /**
     *  按照plateKey分组
     * @param projectId 项目id
     * @param processId 流程id
     * @return 分组后的数据
     */
    Map<String,List<PlateLine>> groupByPlateKey(ProjectId projectId, ProcessId processId);
}
