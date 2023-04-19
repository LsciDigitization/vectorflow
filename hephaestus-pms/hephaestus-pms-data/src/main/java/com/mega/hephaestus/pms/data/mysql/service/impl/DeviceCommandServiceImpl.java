package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceCommandEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.DeviceCommandMapper;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 *
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class DeviceCommandServiceImpl extends
        ServiceImpl<DeviceCommandMapper, DeviceCommandEntity> implements IDeviceCommandService {



}
