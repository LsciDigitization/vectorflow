package com.mega.component.workflow.admin;

import com.mega.component.workflow.models.RunId;
import com.mega.component.workflow.models.TaskId;
import java.util.List;
import java.util.Map;

/**
 * Admin operations
 */
public interface WorkflowAdmin
{
    /**
     * Return all run IDs completed or currently executing
     * in the workflow manager
     *
     * @return run infos
     */
    List<RunId> getRunIds();

    /**
     * Return info about all runs completed or currently executing
     * in the workflow manager
     *
     * @return run infos
     */
    List<RunInfo> getRunInfo();

    /**
     * Return info about the given run
     *
     * @param runId run
     * @return info
     */
    RunInfo getRunInfo(RunId runId);

    /**
     * Return info about all the tasks completed, started or waiting for
     * the given run
     *
     * @param runId run
     * @return task infos
     */
    List<TaskInfo> getTaskInfo(RunId runId);

    /**
     * Returns a map of all task details for the given run
     *
     * @param runId run
     * @return task details
     */
    Map<TaskId, TaskDetails> getTaskDetails(RunId runId);

    /**
     * Delete all saved data for the given run.
     *
     * @param runId the run
     * @return true if the run was found
     */
    boolean clean(RunId runId);

    /**
     * Return information about the internal run/state of the workflow manager
     *
     * @return state
     */
    WorkflowManagerState getWorkflowManagerState();
}
