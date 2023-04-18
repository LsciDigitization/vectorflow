package com.mega.component.task.executor;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mega.component.task.MultiTask;
import com.mega.component.task.Task;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

/**
 * @author toto 多子任务执行器，每个父任务包含多个子任务，父类任务串行执行，各子任务间并发执行
 */
public class MultiTaskExecutor extends TaskExecutor<MultiTask> {
    private final static String EXECUTORNAME = "Multi Task Executor";
    private final static int TASKFINISHED = 1;
    private ExecutorService executor;
    private CompletionService<Integer> completionExecutor;

    public MultiTaskExecutor(int subTaskRunTreadNum, int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        Init(subTaskRunTreadNum);
    }

    public MultiTaskExecutor(int subTaskRunTreadNum) {
        super(defaultAliveTime, defaultCapacity);
        Init(subTaskRunTreadNum);
    }

    private void Init(int subTaskRunTreadNum) {
        this.executor = Executors.newFixedThreadPool(subTaskRunTreadNum, new ThreadFactory(EXECUTORNAME));
        ((ThreadPoolExecutor) this.executor).setKeepAliveTime(10, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) this.executor).allowCoreThreadTimeOut(true); // 设置允许超时回收
        this.completionExecutor = new ExecutorCompletionService<>(executor);

    }

    @Override
    public void execute(final MultiTask multiTask) {
        if (multiTask.isInterrupted()) {
            dispatcher.getLogger().info("Task is interrupted,ID: " + multiTask.getTaskId());
            return;
        }
        multiTask.runTask();
        TaskManager.getInstance().update(new TaskStatus(multiTask.getTaskId(), 50));

        List<Task> subTasks = multiTask.getSubTasks();

        for (Task task : subTasks) {
            completionExecutor.submit(task::runTask, TASKFINISHED);
        }

        // 取出所有子任务完成结果
        for (Task subTask : subTasks) {
            try {
                // 阻塞，直至有一个任务完成，并取回结果
                Future<Integer> future = completionExecutor.take();
                if (future.get() == TASKFINISHED) {
                    dispatcher.getLogger().info("SubTask :" + subTask.getTaskId() + " is finished.");
                }
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        multiTask.taskFinished();
    }

    @Override
    public void closeExecutor() {
        // TODO Auto-generated method stub
        this.executor.shutdown();
        dispatcher.getLogger().info(EXECUTORNAME + " shut down.");
    }
}
