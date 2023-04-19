package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.FlowStepConditionEntity;

import java.util.List;

/**
 * FlowStepConditionEntity
 *
 * @author xianming.hu
 */
public interface IFlowStepConditionService extends IService<FlowStepConditionEntity> {
    /**
     * 根据项目拿到 step条件
     * @param projectId 项目id
     * @return List<FlowStepConditionEntity>
     */
    List<FlowStepConditionEntity> listByProjectId(long projectId);


    /**
     * 根据stepKey 拿到step条件
     * @param projectId 项目id
     * @param stepKey 步骤key
     * @return List<FlowStepConditionEntity>
     */
    List<FlowStepConditionEntity> ListByStepKey(long projectId, String stepKey);

}

