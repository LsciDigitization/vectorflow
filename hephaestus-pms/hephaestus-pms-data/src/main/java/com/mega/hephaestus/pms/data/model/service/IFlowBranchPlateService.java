package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.FlowBranchPlateEntity;

import java.util.List;

/**
 *
 *
 * @author xianming.hu
 */
public interface IFlowBranchPlateService extends IService<FlowBranchPlateEntity> {


    /**
     * 根据 分支keys 拿到分支耗材对应关系
     * @param projectId 项目id
     * @param branchKeys 分支key
     * @return 分支耗材对应关系
     */
    List<FlowBranchPlateEntity> listByProjectIdAndInBranchKeys(long projectId, List<String> branchKeys);

}

