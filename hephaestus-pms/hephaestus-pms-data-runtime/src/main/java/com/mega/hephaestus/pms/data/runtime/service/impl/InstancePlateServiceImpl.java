package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.view.PlateNoCountView;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;
import com.mega.hephaestus.pms.data.runtime.mapper.InstancePlateMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstancePlateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * 实例板池service
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class InstancePlateServiceImpl extends
        ServiceImpl<InstancePlateMapper, HephaestusInstancePlate> implements IInstancePlateService {

    /**
     * 根据池类型和是否消费  统计总数
     *
     * @param historyId          实验组id
     * @param experimentPoolType 池类型
     * @param booleanEnum        是否消费
     * @return 数量
     */
    @Override
    public long countByHistoryIdPoolTypeAndIsConsumed(long historyId, String experimentPoolType, BooleanEnum booleanEnum) {
        return lambdaQuery()
                .eq(HephaestusInstancePlate::getExperimentGroupHistoryId, historyId)
                .eq(HephaestusInstancePlate::getExperimentPoolType, experimentPoolType)
                .eq(Objects.nonNull(booleanEnum), HephaestusInstancePlate::getIsConsumed, booleanEnum)
                .count();
    }

    /**
     * 根据 实验组历史id、池类型和是否消费  获取板池
     *
     * @param historyId          实验组历史id
     * @param experimentPoolType 池类型
     * @param booleanEnum        是否消费
     * @return 板池对象
     */
    @Override
    public Optional<HephaestusInstancePlate> getByHistoryIdPoolTypeAndIsConsumed(long historyId, String experimentPoolType, BooleanEnum booleanEnum) {
        List<HephaestusInstancePlate> list = lambdaQuery()
                .eq(HephaestusInstancePlate::getExperimentGroupHistoryId, historyId)
                .eq(HephaestusInstancePlate::getExperimentPoolType, experimentPoolType)
                .eq(Objects.nonNull(booleanEnum), HephaestusInstancePlate::getIsConsumed, booleanEnum)
                .orderByAsc(HephaestusInstancePlate::getPlateNo)
                .last("limit 1")
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<HephaestusInstancePlate> getByHistoryIdPoolTypeAndPlateNoAndIsConsumed(long historyId, String experimentPoolType, int plateNo, BooleanEnum booleanEnum) {
        List<HephaestusInstancePlate> list = lambdaQuery()
                .eq(HephaestusInstancePlate::getExperimentGroupHistoryId, historyId)
                .eq(HephaestusInstancePlate::getExperimentPoolType, experimentPoolType)
                .eq(HephaestusInstancePlate::getPlateNo, plateNo)
                .eq(Objects.nonNull(booleanEnum), HephaestusInstancePlate::getIsConsumed, booleanEnum)
                .orderByAsc(HephaestusInstancePlate::getPlateNo)
                .last("limit 1")
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<HephaestusInstancePlate> info(String experimentPoolType, Boolean isConsumed) {
        return lambdaQuery()
                .eq(StringUtils.isNotEmpty(experimentPoolType), HephaestusInstancePlate::getExperimentPoolType, experimentPoolType)
                .eq(Objects.nonNull(isConsumed), HephaestusInstancePlate::getIsConsumed, isConsumed).list();
    }

    /**
     * 根据实例id查询板
     *
     * @param instanceId 实例id
     * @return Optional<HephaestusInstancePlate>
     */
    @Override
    public Optional<HephaestusInstancePlate> getByInstanceId(long instanceId) {
        HephaestusInstancePlate one = lambdaQuery()
                .eq(HephaestusInstancePlate::getInstanceId, instanceId)
                .one();
        return Optional.ofNullable(one);
    }

    @Override
    public List<HephaestusInstancePlate> getByInstanceIds(List<Long> instanceIds) {
        return lambdaQuery().in(HephaestusInstancePlate::getInstanceId, instanceIds).list();
    }

    /**
     * 根据实验组历史id 查询板列表
     *
     * @param historyId 实验组历史id
     * @return 板列表
     */
    @Override
    public List<HephaestusInstancePlate> listByHistoryId(long historyId) {
        List<HephaestusInstancePlate> list = lambdaQuery().eq(HephaestusInstancePlate::getExperimentGroupHistoryId, historyId).list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }

    /**
     * 按照板号分组统计
     *
     * @return
     */
    @Override
    public List<PlateNoCountView> countGroupByPlateNo() {
        List<PlateNoCountView> list = baseMapper.countGroupByPlateNo();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }

    /**
     * 按照板号分组统计完成的数量
     *
     * @return
     */
    @Override
    public List<PlateNoCountView> countByIsFinishedGroupByPlateNo() {
        List<PlateNoCountView> list = baseMapper.countByIsFinishedGroupByPlateNo();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }

}
