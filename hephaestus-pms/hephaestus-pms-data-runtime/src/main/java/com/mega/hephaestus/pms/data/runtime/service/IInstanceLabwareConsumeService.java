package com.mega.hephaestus.pms.data.runtime.service;


import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceLabwareConsumeEntity;

import java.util.List;
import java.util.Optional;


/**
 *
 *
 * @author xianming.hu
 */
public interface IInstanceLabwareConsumeService extends IBaseService<InstanceLabwareConsumeEntity> {


    /**
     * 根据记录id 查询
     * @param processRecordId 流程记录id
     * @return 耗材consume状态
     */
    List<InstanceLabwareConsumeEntity> listByProcessRecordId(long processRecordId);

    /**
     * 根据实例id 获取板池consume 情况
     * @param instanceId 实例id
     * @return 耗材consume状态
     */
    Optional<InstanceLabwareConsumeEntity> getByInstanceId(long instanceId);

    /**
     * 根据耗材id 获取板池consume 情况
     * @param labwareId 耗材id
     * @return 耗材consume状态
     */
    Optional<InstanceLabwareConsumeEntity> getByInstanceLabwareId(long labwareId,long processRecordId);

}

