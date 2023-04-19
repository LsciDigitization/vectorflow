package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskResultEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceTaskResultMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 实例结果记录
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class InstanceTaskResultServiceImpl extends
        ServiceImpl<InstanceTaskResultMapper, InstanceTaskResultEntity> implements
        IInstanceTaskResultService {


    /**
     * 根据实例id 获取最近的一条记录 按照时间排序获取最新
     *
     * @param instanceId 实例id
     * @return Optional
     */
    @Override
    public Optional<InstanceTaskResultEntity> getLastByInstanceId(long instanceId) {

        List<InstanceTaskResultEntity> list = lambdaQuery().eq(InstanceTaskResultEntity::getInstanceId, instanceId)
                .orderByDesc(InstanceTaskResultEntity::getCreateTime)
                .last("limit 1")
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }

        return Optional.empty();
    }
}
