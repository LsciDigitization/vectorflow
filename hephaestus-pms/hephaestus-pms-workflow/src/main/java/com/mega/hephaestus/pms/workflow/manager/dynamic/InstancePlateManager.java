package com.mega.hephaestus.pms.workflow.manager.dynamic;


import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;
import com.mega.component.nuc.plate.PlateTypeEnum;

import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 * InstancePlateManager
 */
public interface InstancePlateManager {

    /**
     * push 一个新的板池
     *
     * @param instancePlate 板池
     */
    void push(HephaestusInstancePlate instancePlate);

    /**
     * 根据池类型和实验组历史id 统计未消费板池数量
     *
     * @param poolType 池类型
     * @return 数量
     */
    long sizeByPoolType(long historyId, PlateTypeEnum poolType);

    /**
     * 根据类型获取板池总数
     *
     * @param poolType 池类型
     * @return 数量
     */
    long fullSizeByPoolType(long historyId, PlateTypeEnum poolType);

    /**
     * 获取一个未消费的  板池
     * <p> SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), sample(String), 0(Integer)
     *
     * @param historyId 历史组id
     * @param poolType  池类型
     * @return optional
     */
    Optional<HephaestusInstancePlate> getNonConsumed(long historyId, PlateTypeEnum poolType);

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
    Optional<HephaestusInstancePlate> getNonConsumed(long historyId, PlateTypeEnum poolType, int plateNo);

    /**
     * 消费一个板池
     *
     * @param id         主键iD
     * @param instanceId 实例id
     * @return 是否成功
     */
    boolean consumePlate(long id, long instanceId);

    /**
     * 根据实例id 获取板
     *
     * @param instanceId 实例id
     * @return Optional<HephaestusInstancePlate> 板
     */
    Optional<HephaestusInstancePlate> getInstancePlate(long instanceId);

    /**
     * 根据板类型拿到 实验id
     *
     * @param poolTypeEnum 板类型
     * @return 实验id
     */
    long getExperimentId(PlateTypeEnum poolTypeEnum, long historyId);

    /**
     * 根据结束板子
     *
     * @param instanceId 实例id
     */
    void finishedPlate(long instanceId);

    /**
     * 根据实验组id 获取所有板子
     *
     * @param groupHistoryId 组id
     * @return List<HephaestusInstancePlate>
     */
    List<HephaestusInstancePlate> getInstancePlateByGroupHistoryId(long groupHistoryId);

}
