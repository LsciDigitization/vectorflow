package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import com.mega.hephaestus.pms.data.model.mapper.ExperimentGroupPoolMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusExperimentGroupPoolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 实验组关系
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusExperimentGroupPoolServiceImpl extends
        ServiceImpl<ExperimentGroupPoolMapper, HephaestusExperimentGroupPool> implements IHephaestusExperimentGroupPoolService {

    /**
     * 根据实验组id 修改池子状态为删除
     * 此表是多对多情况，因此会存在删除多条情况
     *
     * @param experimentGroupId 实验组id
     * @return 是否成功
     */
    @Override
    public boolean updateExperimentGroupId(long experimentGroupId) {
        HephaestusExperimentGroupPool groupPool = new HephaestusExperimentGroupPool();
        groupPool.setIsDeleted(BooleanEnum.YES);
        return lambdaUpdate().eq(HephaestusExperimentGroupPool::getExperimentGroupId, experimentGroupId)
                .update(groupPool);
    }

    /**
     * 根据实验组id 查询池
     *
     * @param experimentGroupId 实验组id
     * @return 池信息
     */
    @Override
    public List<HephaestusExperimentGroupPool> listByExperimentGroupId(long experimentGroupId) {
        List<HephaestusExperimentGroupPool> list = lambdaQuery()
                .eq(HephaestusExperimentGroupPool::getExperimentGroupId, experimentGroupId)
                .eq(HephaestusExperimentGroupPool::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public Optional<HephaestusExperimentGroupPool> getByIdForNoDeleted(long id) {
        HephaestusExperimentGroupPool one = lambdaQuery()
                .eq(HephaestusExperimentGroupPool::getId, id)
                .eq(HephaestusExperimentGroupPool::getIsDeleted, BooleanEnum.NO)
                .one();
        return Optional.ofNullable(one);
    }

}
