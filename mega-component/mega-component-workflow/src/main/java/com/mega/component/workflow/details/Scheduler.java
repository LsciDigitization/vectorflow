package com.mega.component.workflow.details;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mega.component.workflow.admin.WorkflowManagerState;
import com.mega.component.workflow.details.internalmodels.RunnableTask;
import com.mega.component.workflow.details.internalmodels.StartedTask;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.RunId;
import com.mega.component.workflow.models.TaskExecutionResult;
import com.mega.component.workflow.models.TaskId;
import com.mega.component.workflow.models.TaskType;
import com.mega.component.workflow.queue.Queue;
import com.mega.component.workflow.queue.QueueFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class Scheduler
{
    @VisibleForTesting
    static volatile AtomicInteger debugBadRunIdCount;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final WorkflowManagerImpl workflowManager;
    private final QueueFactory queueFactory;
    private final AutoCleanerHolder autoCleanerHolder;
    private final PathChildrenCache completedTasksCache;
    private final PathChildrenCache startedTasksCache;
    private final PathChildrenCache runsCache;
    private final AtomicReference<WorkflowManagerState.State> state = new AtomicReference<>(WorkflowManagerState.State.LATENT);
    private final LoadingCache<TaskType, Queue> queues = CacheBuilder.newBuilder()
        .removalListener(Scheduler::remover)
        .build(new CacheLoader<TaskType, Queue>()
        {
            @Override
            public Queue load(TaskType taskType) throws Exception
            {
                log.info("Adding producer queue for: " + taskType);
                Queue queue = queueFactory.createQueue(workflowManager, taskType);
                queue.start();
                return queue;
            }
        });

    private static void remover(RemovalNotification<TaskType, Queue> notification)
    {
        CloseableUtils.closeQuietly(notification.getValue());
    }

    Scheduler(WorkflowManagerImpl workflowManager, QueueFactory queueFactory, AutoCleanerHolder autoCleanerHolder)
    {
        this.workflowManager = workflowManager;
        this.queueFactory = queueFactory;
        this.autoCleanerHolder = autoCleanerHolder;

        completedTasksCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getCompletedTaskParentPath(), true);
        startedTasksCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getStartedTasksParentPath(), false);
        runsCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getRunParentPath(), true);
    }

    WorkflowManagerState.State getState()
    {
        return state.get();
    }

    void run()
    {
        CountDownLatch initLatch = new CountDownLatch(2);

        BlockingQueue<RunId> updatedRunIds = Queues.newLinkedBlockingQueue();
        completedTasksCache.getListenable().addListener((client, event) -> {
            if ( event.getType() == PathChildrenCacheEvent.Type.INITIALIZED )
            {
                initLatch.countDown();
            }
            else if ( event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED )
            {
                RunId runId = new RunId(ZooKeeperConstants.getRunIdFromCompletedTasksPath(event.getData().getPath()));
                updatedRunIds.add(runId);
            }
        });
        runsCache.getListenable().addListener((client, event) -> {
            if ( event.getType() == PathChildrenCacheEvent.Type.INITIALIZED )
            {
                initLatch.countDown();
            }
            else if ( event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED )
            {
                RunId runId = new RunId(ZooKeeperConstants.getRunIdFromRunPath(event.getData().getPath()));
                updatedRunIds.add(runId);
            }
            else if ( event.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED )
            {
                RunnableTask runnableTask = workflowManager.getSerializer().deserialize(event.getData().getData(), RunnableTask.class);
                if ( runnableTask.getParentRunId().isPresent() )
                {
                    updatedRunIds.add(runnableTask.getParentRunId().get());
                }
            }
        });

        try
        {
            completedTasksCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            startedTasksCache.start(PathChildrenCache.StartMode.NORMAL);
            runsCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

            state.set(WorkflowManagerState.State.SLEEPING);
            initLatch.await();
            log.debug("initLatch completed");

            while ( !Thread.currentThread().isInterrupted() )
            {
                state.set(WorkflowManagerState.State.SLEEPING);
                RunId runId = updatedRunIds.poll(autoCleanerHolder.getRunPeriod().toMillis(), TimeUnit.MILLISECONDS);
                state.set(WorkflowManagerState.State.PROCESSING);
                if ( runId != null )
                {
                    updateTasks(runId);
                }
                if ( autoCleanerHolder.shouldRun() )
                {
                    autoCleanerHolder.run(workflowManager.getAdmin());
                }
            }
        }
        catch ( InterruptedException dummy )
        {
            Thread.currentThread().interrupt();
        }
        catch ( Throwable e )
        {
            log.error("Error while running scheduler", e);
        }
        finally
        {
            state.set(WorkflowManagerState.State.CLOSED);
            queues.invalidateAll();
            queues.cleanUp();
            CloseableUtils.closeQuietly(completedTasksCache);
            CloseableUtils.closeQuietly(startedTasksCache);
            CloseableUtils.closeQuietly(runsCache);
        }
    }

    private boolean hasCanceledTasks(RunId runId, RunnableTask runnableTask)
    {
        return runnableTask.getTasks().keySet().stream().anyMatch(taskId -> {
            String completedTaskPath = ZooKeeperConstants.getCompletedTaskPath(runId, taskId);
            ChildData currentData = completedTasksCache.getCurrentData(completedTaskPath);
            if ( currentData != null )
            {
                TaskExecutionResult taskExecutionResult = workflowManager.getSerializer().deserialize(currentData.getData(), TaskExecutionResult.class);
                return taskExecutionResult.getStatus().isCancelingStatus();
            }
            return false;
        });
    }

    static void completeRunnableTask(Logger log, WorkflowManagerImpl workflowManager, RunId runId, RunnableTask runnableTask, int version)
    {
        log.info("Completing run: " + runId);
        try
        {
            RunId parentRunId = runnableTask.getParentRunId().orElse(null);
            RunnableTask completedRunnableTask = new RunnableTask(runnableTask.getTasks(), runnableTask.getTaskDags(), runnableTask.getStartTimeUtc(), LocalDateTime.now(Clock.systemUTC()), parentRunId);
            String runPath = ZooKeeperConstants.getRunPath(runId);
            byte[] json = workflowManager.getSerializer().serialize(completedRunnableTask);
            workflowManager.getCurator().setData().withVersion(version).forPath(runPath, json);
        }
        catch ( Exception e )
        {
            String message = "Could not write completed task data for run: " + runId;
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private void updateTasks(RunId runId)
    {
        log.info("Updating run: " + runId);

        RunnableTask runnableTask = getRunnableTask(runId);
        if ( runnableTask == null )
        {
            if ( debugBadRunIdCount != null )
            {
                debugBadRunIdCount.incrementAndGet();
            }

            log.warn("Could not find run for RunId: " + runId + " - ignoring");
            return;
        }
        if ( runnableTask.getCompletionTimeUtc().isPresent() )
        {
            log.debug("Run is completed. Ignoring: " + runId);
            return;
        }

        if ( hasCanceledTasks(runId, runnableTask) )
        {
            log.debug("Run has canceled tasks and will be marked completed: " + runId);
            completeRunnableTask(log, workflowManager, runId, runnableTask, -1);
            return; // one or more tasks has canceled the entire run
        }

        Set<TaskId> completedTasks = Sets.newHashSet();
        runnableTask.getTaskDags().forEach(entry -> {
            TaskId taskId = entry.getTaskId();
            ExecutableTask task = runnableTask.getTasks().get(taskId);
            if ( task == null )
            {
                log.error(String.format("Could not find task: %s for run: %s", taskId, runId));
                return;
            }
            
            boolean taskIsComplete = taskIsComplete(completedTasksCache, runId, task);
            if ( taskIsComplete )
            {
                completedTasks.add(taskId);
            }
            else if ( !taskIsStarted(startedTasksCache, runId, taskId) )
            {
                boolean allDependenciesAreComplete = entry
                    .getDependencies()
                    .stream()
                    .allMatch(id -> taskIsComplete(completedTasksCache, runId, runnableTask.getTasks().get(id)));
                if ( allDependenciesAreComplete )
                {
                    queueTask(runId, task);
                }
            }
        });

        if ( completedTasks.equals(runnableTask.getTasks().keySet()))
        {
            completeRunnableTask(log, workflowManager, runId, runnableTask, -1);
        }
    }

    private RunnableTask getRunnableTask(RunId runId)
    {
        ChildData currentData = runsCache.getCurrentData(ZooKeeperConstants.getRunPath(runId));
        if ( currentData != null )
        {
            return workflowManager.getSerializer().deserialize(currentData.getData(), RunnableTask.class);
        }
        return null;
    }

    private void queueTask(RunId runId, ExecutableTask task)
    {
        String path = ZooKeeperConstants.getStartedTaskPath(runId, task.getTaskId());
        try
        {
            StartedTask startedTask = new StartedTask(workflowManager.getInstanceName(), LocalDateTime.now(Clock.systemUTC()), 0);
            byte[] data = workflowManager.getSerializer().serialize(startedTask);
            workflowManager.getCurator().create().creatingParentContainersIfNeeded().forPath(path, data);
            Queue queue = queues.get(task.getTaskType());
            queue.put(task);
            log.info("Queued task: " + task);
        }
        catch ( KeeperException.NodeExistsException ignore )
        {
            log.debug("Task already queued: " + task);
            // race due to caching latency - task already started
        }
        catch ( Exception e )
        {
            String message = "Could not start task " + task;
            log.error(message, e);
            throw new RuntimeException(e);
        }
    }

    private boolean taskIsStarted(PathChildrenCache startedTasksCache, RunId runId, TaskId taskId)
    {
        String startedTaskPath = ZooKeeperConstants.getStartedTaskPath(runId, taskId);
        return (startedTasksCache.getCurrentData(startedTaskPath) != null);
    }

    private boolean taskIsComplete(PathChildrenCache completedTasksCache, RunId runId, ExecutableTask task)
    {
        if ( (task == null) || !task.isExecutable() )
        {
            return true;
        }
        String completedTaskPath = ZooKeeperConstants.getCompletedTaskPath(runId, task.getTaskId());
        ChildData currentData = completedTasksCache.getCurrentData(completedTaskPath);
        if ( currentData != null )
        {
            TaskExecutionResult result = workflowManager.getSerializer().deserialize(currentData.getData(), TaskExecutionResult.class);
            if ( result.getSubTaskRunId().isPresent() )
            {
                RunnableTask runnableTask = getRunnableTask(result.getSubTaskRunId().get());
                return (runnableTask != null) && runnableTask.getCompletionTimeUtc().isPresent();
            }
            return true;
        }
        return false;
    }
}
