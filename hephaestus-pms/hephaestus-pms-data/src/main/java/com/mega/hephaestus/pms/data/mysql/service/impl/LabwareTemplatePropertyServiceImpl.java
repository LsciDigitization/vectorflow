package com.mega.hephaestus.pms.data.mysql.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTemplatePropertyEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareTemplatePropertyMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTemplatePropertyService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
/**
 * 耗材模板属性
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwareTemplatePropertyServiceImpl extends
        ServiceImpl<LabwareTemplatePropertyMapper, LabwareTemplatePropertyEntity> implements ILabwareTemplatePropertyService {

}
