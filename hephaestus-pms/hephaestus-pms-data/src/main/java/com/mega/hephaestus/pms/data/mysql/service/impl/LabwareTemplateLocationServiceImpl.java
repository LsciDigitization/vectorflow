package com.mega.hephaestus.pms.data.mysql.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTemplateLocationEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareTemplateLocationMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTemplateLocationService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

/**
 * 耗材模板位置
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwareTemplateLocationServiceImpl extends
        ServiceImpl<LabwareTemplateLocationMapper, LabwareTemplateLocationEntity> implements ILabwareTemplateLocationService {


}
