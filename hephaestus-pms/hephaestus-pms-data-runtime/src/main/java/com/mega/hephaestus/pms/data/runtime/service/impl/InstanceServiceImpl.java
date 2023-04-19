package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceExperimentView;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class InstanceServiceImpl extends
        ServiceImpl<InstanceMapper, InstanceEntity> implements IInstanceService {
    /**
     * 今日完成实验数量
     *
     * @return Optional<Long> 数量
     */
    @Override
    public Optional<Long> countByToDayAndComplete() {
        Date startTime = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        Long count = lambdaQuery().eq(InstanceEntity::getInstanceStatus, ExperimentInstanceStatusEnum.FINISHED)
                .between(InstanceEntity::getCreateTime, startTime, endTime)
                .count();
        return Optional.ofNullable(count);
    }

    /**
     * 今日未完成实验数量
     *
     * @return Optional<Long> 数量
     */
    @Override
    public Optional<Long> countByToDayAndUnfinished() {
        Date startTime = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        Long count = lambdaQuery()
                .lt(InstanceEntity::getInstanceStatus, ExperimentInstanceStatusEnum.FINISHED)
                .between(InstanceEntity::getCreateTime, startTime, endTime)
                .count();
        return Optional.ofNullable(count);
    }

    /**
     * 获取最近的一次实例
     *
     * @return  Optional<InstanceEntity>
     */
    @Override
    public Optional<InstanceEntity> getLastHephaestusInstanceByCreateTime() {
        LambdaQueryChainWrapper<InstanceEntity> wrapper  = lambdaQuery().orderByDesc(InstanceEntity::getCreateTime);
        wrapper.last("limit 1");
        return Optional.ofNullable(wrapper.one());
    }

    /**
     * 根据实例id 联查出实验信息
     *
     * @param instanceId 实例id
     * @return Optional<HephaestusInstanceExperiment>
     */
    @Override
    public Optional<HephaestusInstanceExperimentView> getByInstanceId(long instanceId) {
        return Optional.ofNullable(this.baseMapper.getInstanceExperiment(instanceId));
    }

    /**
     * 根据状态查询
     *
     * @param instanceStatus 状态
     * @return Optional<List < InstanceEntity>>
     */
    @Override
    public List<InstanceEntity> listByInstanceStatus(ExperimentInstanceStatusEnum instanceStatus) {
        List<InstanceEntity> list = lambdaQuery()
                .eq(InstanceEntity::getInstanceStatus, instanceStatus.getValue())
                .list();
        if(CollectionUtils.isEmpty(list)){
            return List.of();
        }
        return list;
    }


    @Override
    public Optional<InstanceEntity> getByIdForNoDeleted(long id) {
        InstanceEntity one = lambdaQuery()
                .eq(InstanceEntity::getId, id)
                .eq(InstanceEntity::getIsDeleted, BooleanEnum.NO)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据实验组历史id查询 instance列表
     *
     * @param processRecordId 实验组历史表
     * @return 实验组集合
     */
    @Override
    public List<InstanceEntity> listByExperimentGroupHistoryId(Long processRecordId, Long experimentId) {
        List<InstanceEntity> list = lambdaQuery()
                .eq(Objects.nonNull(processRecordId), InstanceEntity::getProcessRecordId, processRecordId)
                .eq(Objects.nonNull(experimentId), InstanceEntity::getExperimentId, experimentId)
                .list();
        if(CollectionUtils.isEmpty(list)){
            return List.of();
        }
        return list;
    }

    @Override
    public List<InstanceEntity> listByIds(List<Long> ids) {
        List<InstanceEntity> list = lambdaQuery()
                .in(CollectionUtils.isNotEmpty(ids), InstanceEntity::getId, ids)
                .list();
        if(CollectionUtils.isEmpty(list)){
            return List.of();
        }
        return list;
    }

}
