package com.mega.hephaestus.pms.workflow.task.tasklog;

import com.mega.component.bioflow.task.StageTaskEntity;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskLoggerService {

    public void info(Long instanceId, DeviceTaskEntity task, Object message) {
        log.info(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, null, null, task.getTaskId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Long instanceId, ResourceTaskEntity task, Object message) {
        log.info(message.toString());
        try {
            //todo task表IInstanceId taskId 被删除
         //   experimentInstanceLoggingService.info(instanceId, null, null, task.getTaskId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Long instanceId, HephaestusStageTask task, Object message) {
        log.info(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, task.getExperimentId(), task.getStageId(), task.getId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Long instanceId, StageTaskEntity task, Object message) {
        log.info(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, task.getExperimentId().getLongId(), task.getStageId().getLongId(), task.getId().getLongId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Long instanceId, HephaestusStage stage, Object message) {
        log.info(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, stage.getExperimentId(), stage.getId(), 0L, Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Long instanceId, Object message) {
        log.info(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, 0L, 0L, 0L, Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(Long instanceId, HephaestusStageTask task, Object message) {
        log.error(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, task.getExperimentId(), task.getStageId(), task.getId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(Long instanceId, StageTaskEntity task, Object message) {
        log.error(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, task.getExperimentId().getLongId(), task.getStageId().getLongId(), task.getId().getLongId(), Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(Long instanceId, Object message) {
        log.error(message.toString());
        try {
//            experimentInstanceLoggingService.info(instanceId, 0L, 0L, 0L, Thread.currentThread().getName(), message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
