package com.mega.hephaestus.pms.data.runtime.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceExperimentView;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;

import java.util.List;
import java.util.Optional;

/**
 * @author xianming.hu
 */
public interface IInstanceService extends IBaseService<InstanceEntity> {


    /**
     * 今日完成实验数量
     * @return Optional<Long> 数量
     */
    Optional<Long> countByToDayAndComplete();


    /**
     * 今日未完成实验数量
     * @return Optional<Long> 数量
     */
    Optional<Long> countByToDayAndUnfinished();

    /**
     * 根据时间获取最近的一次实例
     * createtime order desc limit 1
     * @return  Optional<InstanceEntity>
     */
    Optional<InstanceEntity> getLastHephaestusInstanceByCreateTime();

    /**
     * 根据实例id 联查出实验信息
     * @param instanceId 实例id
     * @return  Optional<HephaestusInstanceExperiment>
     */
    Optional<HephaestusInstanceExperimentView> getByInstanceId(long instanceId);

    /**
     * 根据状态查询
     * @param instanceStatus 状态
     * @return Optional<List<InstanceEntity>>
     */
    List<InstanceEntity> listByInstanceStatus(ExperimentInstanceStatusEnum instanceStatus);


    /**
     * 根据主键获取未被isDeleted的数据
     * @param id 主键ID
     * @return InstanceEntity
     */
    Optional<InstanceEntity> getByIdForNoDeleted(long id);


    /**
     * 根据实验组历史id查询 instance列表
     * <p>
     *      if(experimentGroupHistoryId !=null){ </p>
     * <p>      and experiment_group_historyId = experimentGroupHistoryId </p>
     * <p>  } </p>
     * <p>  if(experimentId !=null){ </p>
     *  <p>     and experiment_id = experimentId </p>
     * <p>  } </p>
     *
     * @param processRecordId 实验组历史表
     * @return 实验组集合
     */
    List<InstanceEntity> listByExperimentGroupHistoryId(Long processRecordId, Long experimentId);

    List<InstanceEntity> listByIds(List<Long> ids);


}

