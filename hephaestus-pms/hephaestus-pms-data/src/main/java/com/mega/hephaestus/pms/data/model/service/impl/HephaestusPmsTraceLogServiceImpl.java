package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTraceLog;
import com.mega.hephaestus.pms.data.model.mapper.PmsTraceLogMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusPmsTraceLogService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;


/**
 * PMS操作日志
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusPmsTraceLogServiceImpl extends
        ServiceImpl<PmsTraceLogMapper, HephaestusPmsTraceLog> implements IHephaestusPmsTraceLogService {

}
