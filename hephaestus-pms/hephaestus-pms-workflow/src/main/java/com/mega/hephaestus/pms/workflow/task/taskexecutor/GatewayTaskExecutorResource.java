package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentExecutorManager;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Getter
public class GatewayTaskExecutorResource {
    @Autowired
    private ExperimentExecutorManager experimentExecutorManager;

    @Resource
    private TaskLoggerService taskLoggerService;

}
