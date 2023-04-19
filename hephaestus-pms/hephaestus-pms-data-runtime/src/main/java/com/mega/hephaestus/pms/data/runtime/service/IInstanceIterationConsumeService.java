package com.mega.hephaestus.pms.data.runtime.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceIterationConsumeEntity;

import java.util.List;
import java.util.Optional;


/**
 *  通量消费情况
 *
 * @author xianming.hu
 */
public interface IInstanceIterationConsumeService extends IBaseService<InstanceIterationConsumeEntity> {

    /**
     * 根据通量id 获取通量的消费情况
     * @param iterationId 通量id
     * @return 通量消费情况
     */
    Optional<InstanceIterationConsumeEntity> getByIterationId(long iterationId,long processRecordId);

    /**
     * 根据记录id 获取 通量消费情况
     * @param processRecordId 记录id
     * @return 通量消费情况
     */
    List<InstanceIterationConsumeEntity> listByProcessRecordId(long processRecordId);
}

