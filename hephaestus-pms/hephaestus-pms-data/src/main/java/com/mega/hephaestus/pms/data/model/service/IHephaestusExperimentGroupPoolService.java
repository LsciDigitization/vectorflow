package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;

import java.util.List;
import java.util.Optional;


/**
 * 实验组关系
 *
 * @author xianming.hu
 */
public interface IHephaestusExperimentGroupPoolService extends IService<HephaestusExperimentGroupPool> {


    /**
     * 根据实验组id 修改池子状态为删除
     * 此表是多对多情况，因此会存在删除多条情况
     * @param experimentGroupId 实验组id
     * @return 是否成功
     */
    boolean updateExperimentGroupId(long experimentGroupId);

    /**
     * 根据实验组id 查询池
     * @param experimentGroupId 实验组id
     * @return 池信息
     */
    List<HephaestusExperimentGroupPool>  listByExperimentGroupId(long experimentGroupId);

    /**
     * 根据主键获取未被isDeleted的数据
     * @param id 主键ID
     * @return HephaestusExperimentGroupPool
     */
    Optional<HephaestusExperimentGroupPool> getByIdForNoDeleted(long id);


}

