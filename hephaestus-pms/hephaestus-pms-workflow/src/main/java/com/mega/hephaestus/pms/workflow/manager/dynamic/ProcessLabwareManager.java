package com.mega.hephaestus.pms.workflow.manager.dynamic;


import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 */
public interface ProcessLabwareManager {


    /**
     * 根据实验组历史id 获取实验组池
     * @param processRecordId  流程记录id(原实验组历史id)
     * @return List<ProcessLabwareEntity>
     */
    List<ProcessLabwareEntity> getProcessLabwares(long processRecordId);

    /**
     * 根据流程ID 获取流程耗材池
     * @param processId  流程id(原实验组id)
     * @return List<ProcessLabwareEntity>
     */
    List<ProcessLabwareEntity> getProcessLabwaresByProcessId(long processId);

    /**
     * 根据流程id 拿到通量主任务
     * @param processId 流程id
     * @return  通量主任务
     */
    Optional<ProcessLabwareEntity> getMainByProcessId(long processId);


    /**
     * 根据实验id 拿到对应的耗材流程
     * @param experimentId 实验id
     * @param processId 流程id
     * @return 流程耗材
     */
    Optional<ProcessLabwareEntity> getByExperimentIdAndProcessId(long experimentId,long processId);
}
