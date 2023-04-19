package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.FlowBranchPlateEntity;
import com.mega.hephaestus.pms.data.model.entity.FlowStepBranchEntity;
import com.mega.hephaestus.pms.data.model.mapper.FlowBranchPlateMapper;
import com.mega.hephaestus.pms.data.model.service.IFlowBranchPlateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class FlowBranchPlateServiceImpl extends ServiceImpl<FlowBranchPlateMapper, FlowBranchPlateEntity> implements IFlowBranchPlateService {

    /**
     * 根据 分支keys 拿到分支耗材对应关系
     *
     * @param projectId  项目id
     * @param branchKeys 分支key
     * @return 分支耗材对应关系
     */
    @Override
    public List<FlowBranchPlateEntity> listByProjectIdAndInBranchKeys(long projectId, List<String> branchKeys) {
        List<FlowBranchPlateEntity> list = lambdaQuery()
                .eq(FlowBranchPlateEntity::getProjectId, projectId)
                .in(FlowBranchPlateEntity::getBranchKey,branchKeys)
                .eq(FlowBranchPlateEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;

    }
}
