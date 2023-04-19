package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceLabwareConsumeEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceLabwareConsumeMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceLabwareConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class InstanceLabwareConsumeServiceImpl extends
        ServiceImpl<InstanceLabwareConsumeMapper, InstanceLabwareConsumeEntity> implements IInstanceLabwareConsumeService {


    /**
     * 根据记录id 查询
     *
     * @param processRecordId 流程记录id
     * @return 耗材consume状态
     */
    @Override
    public List<InstanceLabwareConsumeEntity> listByProcessRecordId(long processRecordId) {
        List<InstanceLabwareConsumeEntity> list = lambdaQuery()
                .eq(InstanceLabwareConsumeEntity::getProcessRecordId, processRecordId)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }

    /**
     * 根据实例id 获取板池consume 情况
     *
     * @param instanceId 实例id
     * @return 耗材consume状态
     */
    @Override
    public Optional<InstanceLabwareConsumeEntity> getByInstanceId(long instanceId) {
        InstanceLabwareConsumeEntity one = lambdaQuery().eq(InstanceLabwareConsumeEntity::getInstanceId, instanceId).one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据耗材id 获取板池consume 情况
     *
     * @param labwareId 耗材id
     * @return 耗材consume状态
     */
    @Override
    public Optional<InstanceLabwareConsumeEntity> getByInstanceLabwareId(long labwareId,long processRecordId) {
        InstanceLabwareConsumeEntity one = lambdaQuery()
                .eq(InstanceLabwareConsumeEntity::getInstanceLabwareId, labwareId)
                .eq(InstanceLabwareConsumeEntity::getProcessRecordId,processRecordId)
                .one();
        return Optional.ofNullable(one);
    }
}
