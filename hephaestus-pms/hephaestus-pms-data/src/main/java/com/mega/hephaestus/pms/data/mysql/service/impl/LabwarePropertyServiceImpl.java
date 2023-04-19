package com.mega.hephaestus.pms.data.mysql.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePropertyEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwarePropertyMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePropertyService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * 耗材模板属性
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwarePropertyServiceImpl extends
        ServiceImpl<LabwarePropertyMapper, LabwarePropertyEntity> implements ILabwarePropertyService {


}
