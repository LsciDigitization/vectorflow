package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;
import com.mega.component.nuc.plate.PlateTypeEnum;
import com.mega.hephaestus.pms.data.runtime.service.IInstancePlateService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstancePlateManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:25
 */
@Component
@RequiredArgsConstructor
public class InstancePlateManagerImpl implements InstancePlateManager {

    private final IInstancePlateService plateService;

    /**
     * push 一个新的板池
     *
     * @param instancePlate 板池
     */
    public void push(HephaestusInstancePlate instancePlate) {
        plateService.save(instancePlate);
    }

    /**
     * 根据池类型和实验组历史id 统计未消费板池数量
     *
     * @param poolType 池类型
     * @return 数量
     */
    public long sizeByPoolType(long historyId, PlateTypeEnum poolType) {
        return plateService.countByHistoryIdPoolTypeAndIsConsumed(historyId, poolType.getCode(), BooleanEnum.NO);

    }

    /**
     * 根据类型获取板池总数
     *
     * @param poolType 池类型
     * @return 数量
     */
    public long fullSizeByPoolType(long historyId, PlateTypeEnum poolType) {
        return plateService.countByHistoryIdPoolTypeAndIsConsumed(historyId, poolType.getCode(), null);
    }

    /**
     * 获取一个未消费的  板池
     * <p> SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), sample(String), 0(Integer)
     *
     * @param historyId 历史组id
     * @param poolType  池类型
     * @return optional
     */
    public Optional<HephaestusInstancePlate> getNonConsumed(long historyId, PlateTypeEnum poolType) {
        return plateService.getByHistoryIdPoolTypeAndIsConsumed(historyId, poolType.getCode(), BooleanEnum.NO);
    }

    /**
     * 获取一个未消费的  板池
     * <p>SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND plate_no = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), standard(String), 7(Integer), 0(Integer)
     *
     * @param historyId 历史组id
     * @param poolType  池类型
     * @param plateNo   板序号
     * @return optional
     */
    public Optional<HephaestusInstancePlate> getNonConsumed(long historyId, PlateTypeEnum poolType, int plateNo) {
        return plateService.getByHistoryIdPoolTypeAndPlateNoAndIsConsumed(historyId, poolType.getCode(), plateNo, BooleanEnum.NO);
    }

    /**
     * 消费一个板池
     *
     * @param id         主键iD
     * @param instanceId 实例id
     * @return 是否成功
     */
    public boolean consumePlate(long id, long instanceId) {
        HephaestusInstancePlate instancePlate = new HephaestusInstancePlate();
        instancePlate.setId(id);
        instancePlate.setInstanceId(instanceId);
        instancePlate.setIsConsumed(BooleanEnum.YES);
        instancePlate.setConsumeTime(new Date());
        return plateService.updateById(instancePlate);
    }

    /**
     * 根据实例id 获取板
     *
     * @param instanceId 实例id
     * @return Optional<HephaestusInstancePlate> 板
     */
    public Optional<HephaestusInstancePlate> getInstancePlate(long instanceId) {
        HephaestusInstancePlate instancePlate = plateService.lambdaQuery()
                .eq(HephaestusInstancePlate::getInstanceId, instanceId)
                .one();
        return Optional.ofNullable(instancePlate);
    }

    /**
     * 根据板类型拿到 实验id
     *
     * @param poolTypeEnum 板类型
     * @return 实验id
     */
    public long getExperimentId(PlateTypeEnum poolTypeEnum, long historyId) {
        List<HephaestusInstancePlate> list = plateService.lambdaQuery()
                .eq(HephaestusInstancePlate::getExperimentPoolType, poolTypeEnum.getCode())
                .eq(HephaestusInstancePlate::getExperimentGroupHistoryId, historyId)
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getExperimentId();
        }
        return 0L;
    }

    /**
     * 根据结束板子
     *
     * @param instanceId 实例id
     */
    public void finishedPlate(long instanceId) {
        // 实例完成 修改版子为已完成
        Optional<HephaestusInstancePlate> instancePlateOptional = plateService.getByInstanceId(instanceId);
        if (instancePlateOptional.isPresent()) {
            HephaestusInstancePlate instancePlate = instancePlateOptional.get();
            instancePlate.setIsFinished(BooleanEnum.YES);
            instancePlate.setFinishTime(new Date());
            plateService.updateById(instancePlate);
        }
    }

    /**
     * 根据实验组id 获取所有板子
     *
     * @param groupHistoryId 组id
     * @return List<HephaestusInstancePlate>
     */
    public List<HephaestusInstancePlate> getInstancePlateByGroupHistoryId(long groupHistoryId) {
        List<HephaestusInstancePlate> list = plateService.lambdaQuery()
                .eq(HephaestusInstancePlate::getExperimentGroupHistoryId, groupHistoryId)
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return List.of();
    }

}
