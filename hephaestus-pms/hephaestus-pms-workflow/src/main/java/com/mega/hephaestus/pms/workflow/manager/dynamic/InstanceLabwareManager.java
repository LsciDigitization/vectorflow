package com.mega.hephaestus.pms.workflow.manager.dynamic;


import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;

import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 */
public interface InstanceLabwareManager {
    /**
     * push 一个新的板池
     *
     * @param instancePlate 板池
     */
    void push(InstanceLabwareModel instancePlate);

    /**
     * 根据池类型和实验组历史id 统计未消费板池数量
     *
     * @param
     * @param poolType 池类型
     * @return 数量
     */
    long sizeByPoolType(long processRecordId, String poolType);

    /**
     * 根据类型获取板池总数
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType 池类型
     * @return 数量
     */
    long fullSizeByPoolType(long processRecordId, String poolType);

    /**
     * 获取一个未消费的  板池
     * <p> SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), sample(String), 0(Integer)
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType  池类型
     * @return optional
     */
    Optional<InstanceLabwareModel> getNonConsumed(long processRecordId, String poolType);

    /**
     * 获取一个未消费的  板池
     * <p>SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND plate_no = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), standard(String), 7(Integer), 0(Integer)
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType  池类型
     * @param plateNo   板序号
     * @return optional
     */
    Optional<InstanceLabwareModel> getNonConsumed(long processRecordId, String poolType, int plateNo);

    /**
     * 消费一个耗材
     *
     * @param id         主键iD
     * @param instanceId 实例id
     * @return 是否成功
     */
    boolean consumePlate(long id, long instanceId, long processRecordId);


    /**
     * 根据板类型拿到 实验id
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType 耗材类型
     * @return 实验id
     */
    long getExperimentId(String poolType, long processRecordId);

    /**
     * 根据实例id 标记实例耗材为已完成
     *
     * @param instanceId 实例id
     */
    void finishedPlate(long instanceId);

    /**
     * 根据流程记录id 获取所有耗材
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @return List<LabwarePlateEntity>
     */
    List<InstanceLabwareModel> getInstanceLabwareByProcessRecordId(long processRecordId);

    List<InstanceLabwareModel> listByProcessRecordIdAndLabwareType(long processRecordId,String poolType);
    /**
     * 根据实例id 获取
     * @param instanceId 实例id
     * @return Optional<LabwarePlateEntity>
     */
    Optional<InstanceLabwareModel> getByInstanceId(long instanceId);


    /**
     * 根据通量id 拿到耗材列表
     * @param iterationId 通量id
     * @return 耗材
     */
    List<InstanceLabwareModel> listByIterationId(long iterationId,long processRecordId);
}
