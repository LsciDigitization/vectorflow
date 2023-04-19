package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.mapper.StageMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Stage
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusStageServiceImpl extends
        ServiceImpl<StageMapper, HephaestusStage> implements IHephaestusStageService {

    /**
     * 通过实验ID查找所有的Stage阶段
     *
     * @param experimentId 实验ID
     * @return HephaestusStage list
     */
    public List<HephaestusStage> listByExperimentId(long experimentId) {
        List<HephaestusStage> list = lambdaQuery()
                .eq(HephaestusStage::getExperimentId, experimentId)
                .eq(HephaestusStage::getIsDeleted, BooleanEnum.NO.getCode()) // 未删除的
                .orderByAsc(HephaestusStage::getSortOrder)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }
        return list;
    }

    public Optional<HephaestusStage> firstStage(long experimentId) {
        List<HephaestusStage> stageEntities = listByExperimentId(experimentId);
        return stageEntities.stream().findFirst();
    }

    public Optional<HephaestusStage> getByStageName(long experimentId, String stageName) {
        HephaestusStage one = lambdaQuery()
                .eq(HephaestusStage::getExperimentId, experimentId)
                .eq(HephaestusStage::getStageName, stageName).one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据实验id和任务名称查询任务
     *
     * @param experimentId 实验id
     * @param stageName    任务名称
     * @return HephaestusStage hephaestusStage
     */
    @Override
    public Optional<HephaestusStage> getByExperimentIdAndStageName(long experimentId, String stageName) {
        HephaestusStage one = lambdaQuery()
                .eq(HephaestusStage::getExperimentId, experimentId)
                .eq(HephaestusStage::getStageName, stageName)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 逻辑删除stage
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean removeLogicById(long id) {
        HephaestusStage stage = new HephaestusStage();
        stage.setIsDeleted(BooleanEnum.YES);
        return lambdaUpdate()
                .eq(HephaestusStage::getId, id)
                .update(stage);

    }

}
