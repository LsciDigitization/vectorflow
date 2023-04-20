package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.component.bioflow.task.StageType;
import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;
import com.mega.hephaestus.pms.workflow.task.taskexecutor.*;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypePool;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypeRegister;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
public class TaskTypeConfiguration extends BaseConfiguration<TaskTypeRegister> {

    private final Map<String, TaskTypeRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();
    @Resource
    private TaskTypePool taskTypePool;
    @Resource
    private DynamicStageTaskExecutorResource stageTaskExecutorResource;
    @Resource
    private StartTaskExecutorResource startTaskExecutorResource;
    @Resource
    private EndTaskExecutorResource endTaskExecutorResource;
    @Resource
    private AwaitTaskExecutorResource awaitTaskExecutorResource;
    @Resource
    private GatewayTaskExecutorResource gatewayTaskExecutorResource;
    @Resource
    private ConditionTaskExecutorResource conditionTaskExecutorResource;
    @Resource
    private WorkflowServiceFactory workflowServiceFactory;

    private void assignTaskExecutor(TaskTypeRegister taskTypeRegister, StageType stageType) {
        AbstractStageFlowExecutor taskExecutor;
        switch (stageType) {
            case Await:
                taskExecutor = new AwaitTaskExecutor(1, awaitTaskExecutorResource, awaitTaskExecutorResource.getTaskLoggerService());
                break;
            case Gateway:
                taskExecutor = new GatewayExecutor(1, gatewayTaskExecutorResource, gatewayTaskExecutorResource.getTaskLoggerService());
                break;
            case Start:
                taskExecutor = new StartTaskExecutor(1, startTaskExecutorResource, startTaskExecutorResource.getTaskLoggerService());
                break;
            case End:
                taskExecutor = new EndTaskExecutor(1, endTaskExecutorResource, endTaskExecutorResource.getTaskLoggerService());
                break;
            case Dynamic:
            case Stage:
                taskExecutor = new DynamicStageTaskExecutor(1, stageTaskExecutorResource, stageTaskExecutorResource.getTaskLoggerService());
                break;
            case Condition:
                taskExecutor = new ConditionTaskExecutor(1, conditionTaskExecutorResource, conditionTaskExecutorResource.getTaskLoggerService());
                break;
            default:
                throw new IllegalArgumentException("Unsupported stage type: " + stageType);
        }
        taskTypeRegister.setTaskExecutor(taskExecutor);
    }

    private void registerTaskTypeServices() {
        List<TaskTypeRegister> taskTypeRegisters = workflowServiceFactory.loadTaskTypeRegisterServices();

        for (TaskTypeRegister taskTypeRegister : taskTypeRegisters) {
            assignTaskExecutor(taskTypeRegister, taskTypeRegister.type());
            deviceTypeCallableMap.put(taskTypeRegister.type().getValue(), taskTypeRegister);
        }
    }

    private void populateTaskTypePool() {
        deviceTypeCallableMap.forEach(taskTypePool::add);
    }

    @Override
    protected void registerServices() {
        registerTaskTypeServices();
    }

    @Override
    protected void populatePool() {
        populateTaskTypePool();
    }

}
