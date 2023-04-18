package com.mega.component.task.task;

public class DefaultTaskListener implements TaskListener {

    private final TaskLogger logger;

    public DefaultTaskListener() {
        logger = new SystemOutLogging();
    }

    public DefaultTaskListener(TaskLogger logger) {
        this.logger = logger;
    }

    @Override
    public void onchange(TaskStatus taskStatus) {
        String taskName = TaskManager.getInstance().getTaskName(taskStatus.getTaskId());
        if (taskName != null) {
            logger.info(String.format("任务ID:%s %s 任务执行：%s%%", taskStatus.getTaskId(), taskName, taskStatus.getStatus()));
        } else {
            logger.info(String.format("任务ID:%s 任务执行：%s%%", taskStatus.getTaskId(), taskStatus.getStatus()));
        }
    }
}
