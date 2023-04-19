package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.FlowStepBranchEntity;

import java.util.List;

/**
 * step
 *
 * @author xianming.hu
 */
public interface IFlowStepBranchService extends IService<FlowStepBranchEntity> {


    /**
     * 根据流程id 项目id 拿到 步骤分支
     * @param projectId 项目id
     * @param processId 流程id
     * @param stepKeys 步骤key
     * @return
     */
    List<FlowStepBranchEntity> listByProjectIdAndProcessIdAndInStepKey(long projectId,long processId,List<String> stepKeys);



    List<FlowStepBranchEntity> listByProjectIdAndProcessId(long projectId,long processId);

}

