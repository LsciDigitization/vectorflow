package com.mega.hephaestus.pms.workflow.work.workstart;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;

import java.util.List;
import java.util.Optional;

public interface WorkStartRegister {

    PlateType plateType();

    /**
     * 启动移液枪头线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId);

    /**
     * 启动移液枪头线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId, int plateNo);

    void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks);

    void setWorkBusDaemonResource(WorkBusDaemonResource workBusDaemonResource);

}
