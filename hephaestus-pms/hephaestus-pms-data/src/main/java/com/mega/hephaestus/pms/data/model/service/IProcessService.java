package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;

import java.util.Optional;


/**
 * 实验组
 *
 * @author xianming.hu
 */
public interface IProcessService extends IService<ProcessEntity> {

    /**
     * 根据实验组名查询
     * @param experimentGroupName 实验组名称
     * @return  Optional<ProcessEntity>
     */
    Optional<ProcessEntity> getByExperimentGroupName(String experimentGroupName);

    /**
     * 根据实验组id 修改实验组初始化状态
     * @param id 主键
     * @param status 状态 0 未初始化 1 初始化
     * @return 是否成功
     */
    boolean updateExperimentGroupInitStatusById(long id,int status);
}

