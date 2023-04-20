package com.mega.hephaestus.pms.workflow.work.workpool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.nuc.workflow.TaskResource;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public class WorkThread implements Runnable {

    private final WorkThreadResource workThreadResource;

    private final InstanceEntity instanceEntity;

    public WorkThread(WorkThreadResource workThreadResource, InstanceEntity instanceEntity) {
        this.workThreadResource = workThreadResource;
        this.instanceEntity = instanceEntity;
    }

    @Override
    public void run() {
        log.info("{} running...", this.getClass().getSimpleName());

        try {
            log.info("{} {}", Thread.currentThread().getName(), LocalTime.now());

            // 启动实验
            // 生成工作流资源
            TaskResource taskResource = makeTaskResource(instanceEntity);
            // 执行实验
            workThreadResource.getStageFlowManager().start(taskResource, runId -> {
                // 启动实验状态
                workThreadResource.getExperimentInstanceNewManager().startInstance(instanceEntity, runId.getId());
            });

        } catch (Exception e) {
            log.error("{} run exception: {}", this.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
            log.info("重启线程，继续执行");
            workThreadResource.getWorkflowExecutor().execute(this);
        }
    }

    private TaskResource makeTaskResource(InstanceEntity instanceEntity) throws JsonProcessingException {
        SpecialMeta specialMeta = new SpecialMeta();
        specialMeta.putMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID, instanceEntity.getId().toString());
        String stageRootTaskAsJson = workThreadResource.getExperimentStageManager()
                .getStageRootTaskAsJson(instanceEntity.getExperimentId(), specialMeta);
        return new TaskResource(stageRootTaskAsJson);
    }
}
