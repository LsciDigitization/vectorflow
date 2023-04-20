package com.mega.hephaestus.pms.workflow.manager.dynamic;

import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;

import java.util.Optional;

/**
 * 实验组执行历史操作
 */
public interface ExperimentGroupHistoryManager {

    /**
     * 获取正在运行的实验组
     * @return ProcessRecordEntity Optional
     */
    Optional<ProcessRecordEntity> getRunningGroup();

    /**
     * 修改实验组历史状态为完成
     * @param id 主键
     */
    void finishHistoryGroup(long id);

}
