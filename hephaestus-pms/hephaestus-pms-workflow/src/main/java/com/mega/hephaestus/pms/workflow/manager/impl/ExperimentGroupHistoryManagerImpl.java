package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.enums.ExperimentGroupStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:30
 */
@Component
@Slf4j
public class ExperimentGroupHistoryManagerImpl implements ExperimentGroupHistoryManager {

    @Autowired
    private IProcessRecordService processRecordService;



    // 当前应用名
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 获取正在运行的实验组
     * @return ProcessRecordEntity Optional
     */
    public Optional<ProcessRecordEntity> getRunningGroup() {

        List<ProcessRecordEntity> list = processRecordService.lambdaQuery()
                .eq(ProcessRecordEntity::getProcessStatus, ExperimentGroupStatusEnum.RUNNING.getValue())
                .eq(ProcessRecordEntity::getIsDeleted, BooleanEnum.NO)
                .eq(ProcessRecordEntity::getAgentApplicationName,applicationName)
                .orderByDesc(ProcessRecordEntity::getCreateTime)
                .last("limit 1")
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }

        return Optional.empty();
    }

    /**
     * 修改实验组历史状态为完成
     * @param id 主键
     */
    public void finishHistoryGroup(long id){
        ProcessRecordEntity groupHistory = processRecordService.getById(id);
        if(Objects.nonNull(groupHistory)){
            log.info("修改实验组历史:{},状态为完成",id);
            groupHistory.setProcessStatus(ExperimentGroupStatusEnum.FINISHED.getValue());
            processRecordService.updateById(groupHistory);
        }
    }

}
