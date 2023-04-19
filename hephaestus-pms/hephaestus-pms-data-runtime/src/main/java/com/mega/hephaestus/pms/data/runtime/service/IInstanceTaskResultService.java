package com.mega.hephaestus.pms.data.runtime.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskResultEntity;

import java.util.Optional;


/**
 *  实例结果记录service
 *
 * @author xianming.hu
 */
public interface IInstanceTaskResultService extends IService<InstanceTaskResultEntity> {


  /**
   * 根据实例id 获取最近的一条记录 按照时间排序获取最新
   * @param instanceId 实例id
   * @return Optional
   */
      Optional<InstanceTaskResultEntity> getLastByInstanceId(long instanceId);
}

