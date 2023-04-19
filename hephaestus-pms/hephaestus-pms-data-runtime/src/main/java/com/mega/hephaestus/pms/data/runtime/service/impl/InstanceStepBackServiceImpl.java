package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstanceStepBack;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceStepBackMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepBackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * 实例 step实现类
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceStepBackServiceImpl extends
        ServiceImpl<InstanceStepBackMapper, HephaestusInstanceStepBack> implements IInstanceStepBackService {



    /**
     * 通过实例id 获取对象
     *
     * @param instanceId 实例id
     * @return 对象
     */
    @Override
    public Optional<HephaestusInstanceStepBack> getByInstanceId(long instanceId) {
        HephaestusInstanceStepBack one = lambdaQuery()
                .eq(HephaestusInstanceStepBack::getInstanceId, instanceId)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据实例id 修改实例状态
     *
     * @param instanceId 实例id
     * @param instanceStatus  实例状态
     * @return 是否成功
     */
    @Override
    public boolean updateInstanceStatusByInstanceId(long instanceId, int instanceStatus) {
        HephaestusInstanceStepBack instanceStep = new HephaestusInstanceStepBack();
        instanceStep.setInstanceStatus(instanceStatus);
        return lambdaUpdate().eq(HephaestusInstanceStepBack::getInstanceId, instanceId).update(instanceStep);
    }



}
