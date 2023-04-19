package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.ExperimentEntity;
import com.mega.hephaestus.pms.data.model.mapper.ExperimentMapper;
import com.mega.hephaestus.pms.data.model.service.IExperimentService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


/**
 * 实验管理表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ExperimentServiceImpl extends
        ServiceImpl<ExperimentMapper, ExperimentEntity> implements IExperimentService {
    /**
     * 根据实验名称查询实验
     *
     * @param experimentName 实验名称
     * @return ExperimentEntity 对象
     */
    @Override
    public Optional<ExperimentEntity> getByExperimentName(String experimentName) {
        ExperimentEntity one = lambdaQuery()
                .eq(ExperimentEntity::getExperimentName, experimentName)
                .one();
        return Optional.ofNullable(one);
    }

    @Override
    public Optional<ExperimentEntity> getByIdForNoDeleted(long id) {
        ExperimentEntity one = lambdaQuery()
                .eq(ExperimentEntity::getId, id)
                .eq(ExperimentEntity::getIsDeleted, BooleanEnum.NO)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 逻辑 删除 即修改isDelete 为true
     *
     * @param id 主键
     * @return 是否修改 成功
     */
    @Override
    public boolean removeLogicById(long id) {
        ExperimentEntity experiment = new ExperimentEntity();
        experiment.setIsDeleted(BooleanEnum.YES);
        return lambdaUpdate()
                .eq(ExperimentEntity::getId,id)
                .update(experiment);
    }

}
