package com.mega.hephaestus.pms.data.mysql.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTemplateEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareTemplateMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTemplateService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

/**
 * 耗材模板
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwareTemplateServiceImpl extends
        ServiceImpl<LabwareTemplateMapper, LabwareTemplateEntity> implements ILabwareTemplateService {


}
