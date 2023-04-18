package com.mega.component.workflow.admin;

@FunctionalInterface
public interface AutoCleaner
{
    /**
     * Called for each potential run to determine if it can be cleaned
     *
     * @param runInfo the run
     * @return true if it can be cleaned
     */
    boolean canBeCleaned(RunInfo runInfo);
}
