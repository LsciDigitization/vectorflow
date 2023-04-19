package com.mega.hephaestus.pms.data.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessIterationEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.ProcessIterationMapper;
import com.mega.hephaestus.pms.data.mysql.service.IProcessIterationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  流程通量
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ProcessIterationServiceImpl extends
        ServiceImpl<ProcessIterationMapper, ProcessIterationEntity> implements IProcessIterationService {


    /**
     * 根据流程id 查询通量
     *
     * @param processId 流程id
     * @return 通量
     */
    @Override
    public List<ProcessIterationEntity> listByProcessId(long processId) {
        List<ProcessIterationEntity> list = lambdaQuery()
                .eq(ProcessIterationEntity::getProcessId, processId)
                .eq(ProcessIterationEntity::getIsDeleted, BooleanEnum.NO)
                .orderByAsc(ProcessIterationEntity::getIterationNo)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }
}
