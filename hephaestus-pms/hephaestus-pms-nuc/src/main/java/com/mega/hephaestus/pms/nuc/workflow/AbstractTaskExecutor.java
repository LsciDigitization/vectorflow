package com.mega.hephaestus.pms.nuc.workflow;

import com.google.common.collect.Maps;
import com.mega.component.workflow.WorkflowManager;
import com.mega.component.workflow.executor.TaskExecution;
import com.mega.component.workflow.executor.TaskExecutionStatus;
import com.mega.component.workflow.executor.TaskExecutor;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.TaskExecutionResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
public abstract class AbstractTaskExecutor implements TaskExecutor {
    private int latchQty;
    private volatile CountDownLatch latch;

    public AbstractTaskExecutor() {
        this(1);
    }

    public AbstractTaskExecutor(int latchQty) {
        this.latchQty = latchQty;
        latch = new CountDownLatch(latchQty);
    }

    public void initLatchQty(int latchQty) {
        this.latchQty = latchQty;
        reset();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


    public void reset() {
        latch = new CountDownLatch(latchQty);
    }

    @Override
    public TaskExecution newTaskExecution(WorkflowManager workflowManager, ExecutableTask task) {
        return () -> {
            TaskExecutionResult taskExecutionResult = executionSuccessResult();
            try {
                taskExecutionResult = doRun(task);
            } catch (Exception e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException(e.getMessage(), e);
//                e.printStackTrace();
                log.info("中断异常 {}", e.getMessage());
                taskExecutionResult = executionFailedResult();
            } finally {
                latch.countDown();
            }

            return taskExecutionResult;
        };
    }

    protected TaskExecutionResult executionFailedResult() {
        return new TaskExecutionResult(TaskExecutionStatus.FAILED_STOP, "failed", resultData());
    }

    protected TaskExecutionResult executionSuccessResult() {
        return new TaskExecutionResult(TaskExecutionStatus.SUCCESS, "success", resultData());
    }

    protected Map<String, String> resultData() {
        return Maps.newHashMap();
    }

    public abstract TaskExecutionResult doRun(ExecutableTask task) throws InterruptedException;

}
