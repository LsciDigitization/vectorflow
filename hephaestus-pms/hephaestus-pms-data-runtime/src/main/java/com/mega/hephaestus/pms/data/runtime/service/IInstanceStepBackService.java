package com.mega.hephaestus.pms.data.runtime.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstanceStepBack;


import java.util.Optional;


/**
 * 实例step service
 *
 * @author xianming.hu
 */
public interface IInstanceStepBackService extends IService<HephaestusInstanceStepBack> {

    /**
     * 通过实例id 获取对象
     * @param instanceId 实例id
     * @return 对象
     */
    Optional<HephaestusInstanceStepBack> getByInstanceId(long instanceId);
    /**
     * 根据实例id 修改实例状态
     * @param instanceId 实例id
     * @param instanceStatus 实例状态
     * @return 是否成功
     */
    boolean updateInstanceStatusByInstanceId(long instanceId,int instanceStatus);

}

