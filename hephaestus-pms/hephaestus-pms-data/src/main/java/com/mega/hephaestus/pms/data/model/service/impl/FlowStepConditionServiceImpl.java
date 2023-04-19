package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.FlowStepConditionEntity;
import com.mega.hephaestus.pms.data.model.mapper.FlowStepConditionMapper;
import com.mega.hephaestus.pms.data.model.service.IFlowStepConditionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class FlowStepConditionServiceImpl extends ServiceImpl<FlowStepConditionMapper, FlowStepConditionEntity> implements IFlowStepConditionService {


    /**
     * 根据项目拿到 step条件
     *
     * @param projectId 项目id
     * @return List<FlowStepConditionEntity>
     */
    @Override
    public List<FlowStepConditionEntity> listByProjectId(long projectId) {
        List<FlowStepConditionEntity> list = lambdaQuery()
                .eq(FlowStepConditionEntity::getProjectId, projectId)
                .eq(FlowStepConditionEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据stepKey 拿到step条件
     *
     * @param projectId 项目id
     * @param stepKey   步骤key
     * @return List<FlowStepConditionEntity>
     */
    @Override
    public List<FlowStepConditionEntity> ListByStepKey(long projectId, String stepKey) {
        List<FlowStepConditionEntity> list = lambdaQuery()
                .eq(FlowStepConditionEntity::getProjectId, projectId)
                .eq(FlowStepConditionEntity::getStepKey,stepKey)
                .eq(FlowStepConditionEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }
}
