package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.component.bioflow.task.StageEntity;
import com.mega.hephaestus.pms.nuc.workflow.stage.AbstractStage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 15:39
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class DynamicStage extends AbstractStage<DynamicStage.RunParameter> {

    private final DynamicStageRunnerTaskResource dynamicStageRunnerTaskResource;

    @Override
    public void run(RunParameter runParameter) {
        // 前置任务
        if (runParameter.stage.getBeforeTasks().size() > 0) {
            (new DynamicStageRunner(runParameter.instanceId, runParameter.stage.getBeforeTasks(), dynamicStageRunnerTaskResource)).build().running();
        }

        // 任务
        if (runParameter.stage.getTasks().size() > 0) {
            (new DynamicStageRunner(runParameter.instanceId, runParameter.stage.getTasks(), dynamicStageRunnerTaskResource)).build().running();
        }

        // 后置任务
        if (runParameter.stage.getAfterTasks().size() > 0) {
            (new DynamicStageRunner(runParameter.instanceId, runParameter.stage.getAfterTasks(), dynamicStageRunnerTaskResource)).build().running();
        }
    }

    @Data
    public static class RunParameter {
        /**
         * 实例ID
         */
        private InstanceId instanceId;

        private StageEntity stage;

    }
}
