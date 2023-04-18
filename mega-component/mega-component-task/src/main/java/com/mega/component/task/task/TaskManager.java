package com.mega.component.task.task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.mega.component.task.Executor;
import com.mega.component.task.Task;

public class TaskManager {
    private static long globalTaskNo = 0;
    private final static Map<Long, Task> runningTasks = new ConcurrentHashMap<Long, Task>();
    private final static Map<Long, List<TaskListener>> listeners = new ConcurrentHashMap<Long, List<TaskListener>>();
    private final static Map<Long, TaskStatus> taskStatus = new ConcurrentHashMap<Long, TaskStatus>();

    public TaskStatus getTaskStatus(long taskId) {
        return taskStatus.get(taskId);
    }

    public String getTaskName(long taskId) {
        Task task = runningTasks.get(taskId);
        if (Objects.nonNull(task)) {
            return task.getTaskName();
        }
        return null;
    }


    private static final int MAX_TASK_COUNT = 10000;
    private static final int REMOVE_TASK_COUNT = 100;

    private static class TaskManagerFactory {
        private static final TaskManager instance = new TaskManager();
    }

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        return TaskManagerFactory.instance;
    }

    public <T extends Task> void addTask(T task) {
        runningTasks.put(task.getTaskId(), task);
        addTaskStatus(new TaskStatus(task.getTaskId(), TaskStatus.S_READY));
    }

    public <T extends Task> void addTask(T task, Executor<T> executor) {
        executor.add(task);
        addTask(task);
    }

    public <T extends Task> void addTask(T task, Executor<T> executor, TaskListener... listener) {
        executor.add(task);
        addTask(task);
        for (TaskListener taskListener : listener) {
            registerListener(task.getTaskId(), taskListener);
        }
    }

    public void removeTask(Long taskId) {
        runningTasks.remove(taskId);
    }

    public synchronized static long applyTaskId() {
        ++globalTaskNo;
        if (globalTaskNo >= 0xffffff) {
            globalTaskNo = 1;
        }
        return globalTaskNo;
    }


    public void registerListener(long taskID, TaskListener listener) {
        synchronized (listeners) {
            List<TaskListener> list = listeners.computeIfAbsent(taskID, k -> new ArrayList<>());
            list.add(listener);
        }
    }

    public void removeListener(long taskID, TaskListener listener) {
        synchronized (listeners) {
            List<TaskListener> list = listeners.get(taskID);
            if (list != null) {
                list.remove(listener);
            }
        }
    }

    public void addTaskStatus(TaskStatus status) {
        if (status == null) {
            return;
        }
        taskStatus.put(status.getTaskId(), status);
        maintain();
    }

    /**
     * 更新任务状态
     *
     * @param status TaskStatus
     */
    public void update(final TaskStatus status) {
        if (status == null) {
            return;
        }
        taskStatus.put(status.getTaskId(), status);
        List<TaskListener> list = getListeners(status.getTaskId());
        for (TaskListener l : list) {
            try {
                l.onchange(status);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (status.isFinish()) {
            runningTasks.remove(status.getTaskId());
        }
    }

    private List<TaskListener> getListeners(long taskID) {
        List<TaskListener> list = new ArrayList<>();
        List<TaskListener> sp = listeners.get(taskID);
        if (sp != null) {
            list.addAll(sp);
        }
        return list;
    }

    public synchronized void interruptTask(Task task) {
        if (null != runningTasks.get(task.getTaskId())) {
            runningTasks.get(task.getTaskId()).interrupt();
        }
        return;
    }


    /**
     * 维护任务状态记录信息
     * 超过MAX_TASK_COUNT个任务时，删除已经完成的REMOVE_TASK_COUNT个任务
     */
    private void maintain() {
        synchronized (taskStatus) {
            if (taskStatus.size() > MAX_TASK_COUNT) {
                TaskStatus[] tks = taskStatus.values().toArray(new TaskStatus[0]);
                Arrays.sort(tks);
                int count = 0;
                for (TaskStatus t : tks) {
                    long id = t.getTaskId();
                    if (taskStatus.get(id).isFinish()) {
                        taskStatus.remove(id);
                        count++;
                        if (count >= REMOVE_TASK_COUNT) {
                            break;
                        }
                    }
                }
            }
        }

    }

}