package com.mega.hephaestus.pms.data.model.service.impl;


import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.model.mapper.StageTaskMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.mega.component.mybatis.common.service.impl.BaseServiceImpl;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageTaskService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 任务表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusStageTaskServiceImpl extends
        BaseServiceImpl<StageTaskMapper, HephaestusStageTask> implements IHephaestusStageTaskService {

    @Autowired
    private IHephaestusStageService stageService;

    @Autowired
    private StageTaskMapper hephaestusStageTaskMapper;

    @Override
    public List<HephaestusStageTask> listByExperimentId(long experimentId) {
        List<HephaestusStageTask> list = lambdaQuery()
                .eq(HephaestusStageTask::getExperimentId, experimentId)
                .eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode()) // 只查询未关闭的
                .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()) // 只查询未删除的
                .orderByAsc(HephaestusStageTask::getSortOrder)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }
        return list;
    }


    @Override
    public List<HephaestusStageTask> listByExperimentStageId(long experimentId, long stageId) {
        List<HephaestusStageTask> list = lambdaQuery()
                .eq(HephaestusStageTask::getExperimentId, experimentId)
                .eq(HephaestusStageTask::getStageId, stageId)
                .eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode()) // 只查询未关闭的
                .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()) // 只查询未删除的
                .orderByAsc(HephaestusStageTask::getSortOrder)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }
        return list;
    }


    @Override
    public List<HephaestusStageTask> listByExperimentStageName(long experimentId, String stageName) {
        Optional<HephaestusStage> byStageName = stageService.getByStageName(experimentId, stageName);

        List<HephaestusStageTask> stageTasks = List.of();
        if (byStageName.isPresent()) {
            HephaestusStage hephaestusStage = byStageName.get();
            stageTasks = listByExperimentStageId(experimentId, hephaestusStage.getId());
        }

        return stageTasks;
    }

    /**
     * 连接查询设备表
     *
     * @param stageId stageId
     * @return List<HephaestusStageTask> 集合
     */
    @Override
    @Deprecated(since = "20221116关联关系变化")
    public List<HephaestusStageTask> listLeftDeviceByStageId(long stageId) {
        List<HephaestusStageTask> stageTaskLeftDevice = hephaestusStageTaskMapper.findStageTaskLeftDevice(stageId);
        if (CollectionUtils.isEmpty(stageTaskLeftDevice)) {
            return List.of();
        }
        return stageTaskLeftDevice;
    }

    /**
     * 根据实验名称查询实验
     *
     * @param stageTaskName 任务名称
     * @return HephaestusStageTask hephaestusStageTask
     */
    @Override
    public Optional<HephaestusStageTask> getByStageTaskName(String stageTaskName) {
        HephaestusStageTask stageTask = lambdaQuery().eq(HephaestusStageTask::getTaskName, stageTaskName).one();
        return Optional.ofNullable(stageTask);
    }

    /**
     * 根据阶段id 查询所有任务
     *
     * @param stageId 阶段id
     * @return List<HephaestusStageTask> 任务列表
     */
    @Override
    public List<HephaestusStageTask> listByStageId(long stageId) {
        List<HephaestusStageTask> stageTaskList = lambdaQuery()
                                                .eq(HephaestusStageTask::getStageId, stageId)
                                                .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()).list();
        if (CollectionUtils.isEmpty(stageTaskList)) {
            return List.of();
        }
        return stageTaskList;
    }

    /**
     * 根据阶段id 批量查询任务
     *
     * @param ids 根据stage ids 查询
     * @return List<HephaestusStageTask> 任务列表
     */
    @Override
    public List<HephaestusStageTask> listByStageIds(List<Long> ids) {
        List<HephaestusStageTask> stageTaskList = lambdaQuery().in(HephaestusStageTask::getStageId, ids).list();
        if (CollectionUtils.isEmpty(stageTaskList)) {
            return List.of();
        }
        return stageTaskList;
    }

    /**
     * 逻辑删除stageTask
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean removeLogicById(long id) {
        HephaestusStageTask stageTask = new HephaestusStageTask();
        stageTask.setIsDeleted(BooleanEnum.YES);
        return lambdaUpdate().eq(HephaestusStageTask::getId, id).update(stageTask);
    }

}
