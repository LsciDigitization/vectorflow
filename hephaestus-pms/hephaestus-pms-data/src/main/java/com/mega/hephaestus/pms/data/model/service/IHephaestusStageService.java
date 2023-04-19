package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;

import java.util.List;
import java.util.Optional;

/**
 * Stage
 *
 * @author xianming.hu
 */
public interface IHephaestusStageService extends IService<HephaestusStage> {

    List<HephaestusStage> listByExperimentId(long experimentId);

    Optional<HephaestusStage> firstStage(long experimentId);

    Optional<HephaestusStage> getByStageName(long experimentId, String stageName);

    /**
     *  根据实验id和任务名称查询任务
     * @param experimentId 实验id
     * @param stageName 任务名称
     * @return HephaestusStage hephaestusStage
     */
    Optional<HephaestusStage> getByExperimentIdAndStageName(long experimentId, String stageName);

    /**
     * 逻辑删除stage
     * @param id 主键
     * @return 是否成功
     */
    boolean removeLogicById(long id);
}

