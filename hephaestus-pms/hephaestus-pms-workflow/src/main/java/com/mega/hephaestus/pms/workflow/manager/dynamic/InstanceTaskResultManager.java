package com.mega.hephaestus.pms.workflow.manager.dynamic;


/**
 * @author 胡贤明 InstancePlateManager
 */
public interface InstanceTaskResultManager {

    void createInstanceResult(long instanceId, String stepKey, String executionStatus, String executionResult);

}
