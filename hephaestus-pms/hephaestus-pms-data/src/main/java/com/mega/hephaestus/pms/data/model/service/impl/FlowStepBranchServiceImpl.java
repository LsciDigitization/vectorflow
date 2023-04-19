package com.mega.hephaestus.pms.data.model.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.FlowStepBranchEntity;
import com.mega.hephaestus.pms.data.model.entity.FlowStepConditionEntity;
import com.mega.hephaestus.pms.data.model.mapper.FlowStepBranchMapper;
import com.mega.hephaestus.pms.data.model.service.IFlowStepBranchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class FlowStepBranchServiceImpl extends ServiceImpl<FlowStepBranchMapper, FlowStepBranchEntity> implements IFlowStepBranchService {


    /**
     * 根据流程id 项目id 拿到 步骤分支
     *
     * @param projectId 项目id
     * @param processId 流程id
     * @param stepKeys  步骤key
     * @return
     */
    @Override
    public List<FlowStepBranchEntity> listByProjectIdAndProcessIdAndInStepKey(long projectId, long processId, List<String> stepKeys) {
        List<FlowStepBranchEntity> list = lambdaQuery()
                .eq(FlowStepBranchEntity::getProjectId, projectId)
                .eq(FlowStepBranchEntity::getProcessId, processId)
                .in(FlowStepBranchEntity::getStepKey,stepKeys)
                .eq(FlowStepBranchEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<FlowStepBranchEntity> listByProjectIdAndProcessId(long projectId, long processId) {
        List<FlowStepBranchEntity> list = lambdaQuery()
                .eq(FlowStepBranchEntity::getProjectId, projectId)
                .eq(FlowStepBranchEntity::getProcessId, processId)
                .eq(FlowStepBranchEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }
}
