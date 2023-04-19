package com.mega.hephaestus.pms.data.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.VectorProjectMapper;
import com.mega.hephaestus.pms.data.mysql.service.IVectorProjectService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;



/**
 * 项目表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class VectorProjectServiceImpl extends
        ServiceImpl<VectorProjectMapper, ProjectEntity> implements IVectorProjectService {



}
