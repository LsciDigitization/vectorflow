package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;
import com.mega.hephaestus.pms.data.model.mapper.ProcessMapper;
import com.mega.hephaestus.pms.data.model.service.IProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * 实验组
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ProcessServiceImpl extends
        ServiceImpl<ProcessMapper, ProcessEntity> implements IProcessService {

    /**
     * 根据实验组名查询
     *
     * @param experimentGroupName 实验组名称
     * @return Optional<ProcessEntity>
     */
    @Override
    public Optional<ProcessEntity> getByExperimentGroupName(String experimentGroupName) {
        ProcessEntity experimentGroup = lambdaQuery()
                .eq(ProcessEntity::getProcessName, experimentGroupName)
                .one();
        return Optional.ofNullable(experimentGroup);
    }

    /**
     * 根据实验组id 修改实验组初始化状态
     *
     * @param id     主键
     * @param status 状态 0 未初始化 1 初始化
     * @return 是否成功
     */
    @Override
    public boolean updateExperimentGroupInitStatusById(long id, int status) {
        ProcessEntity experimentGroup = new ProcessEntity();
//        experimentGroup.setExperimentGroupInitStatus(status);
        return lambdaUpdate()
                .eq(ProcessEntity::getId,id)
                .update(experimentGroup);
    }
}
