package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;

import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.ProcessRecordMapper;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 实验组
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class ProcessRecordServiceImpl extends
        ServiceImpl<ProcessRecordMapper, ProcessRecordEntity> implements IProcessRecordService {


    /**
     * 获取最后一次 如果有运行的返回运行的 没有运行的返回 最后一个完成的
     *
     * @return 最新的记录
     */
    @Override
    public Optional<ProcessRecordEntity> getLast() {
        List<ProcessRecordEntity> list = lambdaQuery()
                .eq(ProcessRecordEntity::getIsDeleted, BooleanEnum.NO)
                .orderByDesc(ProcessRecordEntity::getCreateTime)
                .last("limit 1")
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProcessRecordEntity> getLastByName(String applicationName) {
        List<ProcessRecordEntity> list = lambdaQuery()
                .eq(ProcessRecordEntity::getAgentApplicationName,applicationName)
                .eq(ProcessRecordEntity::getIsDeleted, BooleanEnum.NO)
                .orderByDesc(ProcessRecordEntity::getCreateTime)
                .last("limit 1")
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

}
