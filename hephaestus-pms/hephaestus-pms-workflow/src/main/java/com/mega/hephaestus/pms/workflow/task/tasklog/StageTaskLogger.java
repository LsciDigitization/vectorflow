package com.mega.hephaestus.pms.workflow.task.tasklog;

import com.mega.component.task.task.TaskLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StageTaskLogger implements TaskLogger {

    private final String instanceId;

    private Long experimentId;
    private Long stageId;
    private Long taskId;

    public StageTaskLogger(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public void info(Object message) {
        log.info(message.toString());
//        try {
////            experimentInstanceLoggingService.info(Long.parseLong(instanceId), experimentId, stageId, taskId, Thread.currentThread().getName(), message.toString());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
    }

}
