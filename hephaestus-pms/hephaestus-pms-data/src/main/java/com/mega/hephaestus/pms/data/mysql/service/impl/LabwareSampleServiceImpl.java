package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareSampleEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareSampleMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareSampleService;
import org.springframework.stereotype.Service;


/**
 * Sample service implementation
 */
@Service
public class LabwareSampleServiceImpl extends
        ServiceImpl<LabwareSampleMapper, LabwareSampleEntity> implements ILabwareSampleService {

}

