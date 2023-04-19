package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceBottleneckEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.ResourceBottleneckMapper;
import com.mega.hephaestus.pms.data.runtime.service.IResourceBottleneckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 资源瓶颈
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class ResourceBottleneckServiceImpl extends
        ServiceImpl<ResourceBottleneckMapper, ResourceBottleneckEntity> implements IResourceBottleneckService {

}
