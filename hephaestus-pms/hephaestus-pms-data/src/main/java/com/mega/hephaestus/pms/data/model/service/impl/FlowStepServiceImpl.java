package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.FlowStepEntity;
import com.mega.hephaestus.pms.data.model.mapper.FlowStepMapper;
import com.mega.hephaestus.pms.data.model.service.IFlowStepService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class FlowStepServiceImpl extends ServiceImpl<FlowStepMapper, FlowStepEntity> implements IFlowStepService {


    @Override
    public List<FlowStepEntity> listByProjectId(long projectId) {
        List<FlowStepEntity> list = lambdaQuery()
                .eq(FlowStepEntity::getProjectId, projectId)
                .eq(FlowStepEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public Optional<FlowStepEntity> getByStepKey(long projectId, String stepKey) {

        FlowStepEntity one = lambdaQuery()
                .eq(FlowStepEntity::getProjectId, projectId)
                .eq(FlowStepEntity::getStepKey, stepKey)
                .eq(FlowStepEntity::getIsDeleted, BooleanEnum.NO)
                .one();
        return Optional.ofNullable(one);
    }
}
