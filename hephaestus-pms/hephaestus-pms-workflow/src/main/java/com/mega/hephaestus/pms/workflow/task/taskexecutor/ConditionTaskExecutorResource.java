package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentExecutorManager;
import com.mega.hephaestus.pms.workflow.manager.ExperimentInstanceManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageTaskManager;
import com.mega.hephaestus.pms.workflow.task.stagetask.DynamicStage;
import com.mega.hephaestus.pms.workflow.task.stagetask.DynamicStageTask;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Getter
public class ConditionTaskExecutorResource {
    @Autowired
    private ExperimentExecutorManager experimentExecutorManager;

    @Autowired
    private ExperimentStageTaskManager experimentStageTaskManager;

    @Autowired
    @Deprecated(since = "20230112")
    private ExperimentInstanceManager experimentInstanceManager;

    @Autowired
    @Deprecated(since = "20230322")
    private DynamicStageTask dynamicStageTask;

    @Autowired
    private DynamicStage dynamicStage;

    @Resource
    private ThreadPoolExecutor deviceLockExecutor;

    @Resource
    private TaskLoggerService taskLoggerService;

}
