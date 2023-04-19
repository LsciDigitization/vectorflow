package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareNodeEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareNodeMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareNodeService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * 耗材节点
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwareNodeServiceImpl extends
        ServiceImpl<LabwareNodeMapper, LabwareNodeEntity> implements ILabwareNodeService {

}
