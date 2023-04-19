package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.FlowStepEntity;

import java.util.List;
import java.util.Optional;

/**
 * step
 *
 * @author xianming.hu
 */
public interface IFlowStepService extends IService<FlowStepEntity> {


    /**
     * 根据项目拿到 step
     * @param projectId 项目id
     * @return List<FlowStepEntity>
     */
    List<FlowStepEntity> listByProjectId(long projectId);


    /**
     * 根据stepKey 拿到step
     * @param projectId 项目id
     * @param stepKey 步骤key
     * @return Optional<FlowStepEntity>
     */
    Optional<FlowStepEntity> getByStepKey(long projectId, String stepKey);


}

