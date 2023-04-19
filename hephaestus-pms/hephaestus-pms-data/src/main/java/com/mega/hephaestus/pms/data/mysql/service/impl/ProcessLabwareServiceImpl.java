package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.ProcessLabwareMapper;
import com.mega.hephaestus.pms.data.mysql.service.IProcessLabwareService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 流程耗材关系
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ProcessLabwareServiceImpl extends
        ServiceImpl<ProcessLabwareMapper, ProcessLabwareEntity> implements IProcessLabwareService {


    /**
     * 根据流程id 获取流程耗材
     *
     * @param processId 流程id
     * @return 流程耗材
     */
    @Override
    public List<ProcessLabwareEntity> listByProcessId(long processId) {
        List<ProcessLabwareEntity> list = lambdaQuery()
                .eq(ProcessLabwareEntity::getProcessId, processId)
                .eq(ProcessLabwareEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据实验id 拿到对应的耗材流程
     *
     * @param experimentId 实验id
     * @param processId    流程id
     * @return 流程耗材
     */
    @Override
    public Optional<ProcessLabwareEntity> getByExperimentIdAndProcessId(long experimentId, long processId) {
        ProcessLabwareEntity one = lambdaQuery()
                .eq(ProcessLabwareEntity::getProcessId, processId)
                .eq(ProcessLabwareEntity::getExperimentId, experimentId)
                .eq(ProcessLabwareEntity::getIsDeleted, BooleanEnum.NO)
                .one();


        return Optional.ofNullable(one);
    }
}
