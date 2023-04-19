package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceStepMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 实例 step实现类
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DS("runtime")
public class InstanceStepServiceImpl extends
        ServiceImpl<InstanceStepMapper, InstanceStepEntity> implements IInstanceStepService {



}
