package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceIterationConsumeEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceIterationConsumeMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceIterationConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *
 * 通量消费情况
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class InstanceIterationConsumeServiceImpl extends
        ServiceImpl<InstanceIterationConsumeMapper, InstanceIterationConsumeEntity> implements IInstanceIterationConsumeService {


    /**
     * 根据通量id 获取通量的消费情况
     *
     * @param iterationId 通量id
     * @return 通量消费情况
     */
    @Override
    public Optional<InstanceIterationConsumeEntity> getByIterationId(long iterationId,long processRecordId) {
        InstanceIterationConsumeEntity one = lambdaQuery()
                .eq(InstanceIterationConsumeEntity::getIterationId, iterationId)
                .eq(InstanceIterationConsumeEntity::getProcessRecordId, processRecordId)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据记录id 获取 通量消费情况
     *
     * @param processRecordId 记录id
     * @return 通量消费情况
     */
    @Override
    public List<InstanceIterationConsumeEntity> listByProcessRecordId(long processRecordId) {
        List<InstanceIterationConsumeEntity> list = lambdaQuery()
                .eq(InstanceIterationConsumeEntity::getProcessRecordId, processRecordId)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }
}
