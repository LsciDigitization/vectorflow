package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.ExperimentEntity;

import java.util.Optional;


/**
 * 实验管理表
 *
 * @author xianming.hu
 */
public interface IExperimentService extends IService<ExperimentEntity> {

    /**
     * 根据实验名称查询实验
     * @param experimentName 实验名称
     * @return  ExperimentEntity 对象
     */
    Optional<ExperimentEntity> getByExperimentName(String experimentName);

    /**
     * 根据主键获取未被isDeleted的数据
     * @param id 主键ID
     * @return ExperimentEntity
     */
    Optional<ExperimentEntity> getByIdForNoDeleted(long id);

//    boolean removeByDelete

    /**
     * 逻辑 删除 即修改isDelete 为true
     * @param id 主键
     * @return 是否修改 成功
     */
    boolean removeLogicById(long id);
}

