package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;

import java.util.List;
import java.util.Optional;


/**
 * 任务表
 *
 * @author xianming.hu
 */
public interface IHephaestusStageTaskService extends IService<HephaestusStageTask> {

    List<HephaestusStageTask> listByExperimentId(long experimentId);

    List<HephaestusStageTask> listByExperimentStageId(long experimentId, long stageId);

    List<HephaestusStageTask> listByExperimentStageName(long experimentId, String stageName);

    /**
     *  连接查询设备表
     * @param stageId stageId
     * @return  List<HephaestusStageTask> 集合
     */
    @Deprecated(since = "20221116关联关系变化")
    List<HephaestusStageTask> listLeftDeviceByStageId(long stageId);

    /**
     * 根据实验名称查询实验
     * @param stageTaskName 实验任务名称
     * @return HephaestusStageTask hephaestusStageTask
     */
    Optional<HephaestusStageTask> getByStageTaskName(String stageTaskName);


    /**
     * 根据阶段id 查询所有任务
     * @param stageId 阶段id
     * @return  List<HephaestusStageTask> 任务列表
     */
    List<HephaestusStageTask> listByStageId(long stageId);



    /**
     * 根据阶段id 批量查询任务
     * @param ids 根据stage ids 查询
     * @return   List<HephaestusStageTask> 任务列表
     */
    List<HephaestusStageTask> listByStageIds(List<Long> ids);


    /**
     * 逻辑删除stageTask
     * @param id 主键
     * @return 是否成功
     */
    boolean removeLogicById(long id);
}

