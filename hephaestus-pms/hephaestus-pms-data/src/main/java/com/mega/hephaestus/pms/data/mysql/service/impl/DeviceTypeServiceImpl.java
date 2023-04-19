package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceTypeEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.DeviceTypeMapper;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 *
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class DeviceTypeServiceImpl extends
        ServiceImpl<DeviceTypeMapper, DeviceTypeEntity> implements IDeviceTypeService {



}
