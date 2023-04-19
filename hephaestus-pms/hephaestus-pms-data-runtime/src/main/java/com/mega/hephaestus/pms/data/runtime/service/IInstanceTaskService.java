package com.mega.hephaestus.pms.data.runtime.service;


import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceTaskView;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * 设备task 接口
 * @author xianming.hu
 */
public interface IInstanceTaskService extends IBaseService<InstanceTaskEntity> {

    /**
     * 根据实例id 获取task
     * @param instanceId 实例id
     * @return 设备task
     */
    Optional<InstanceTaskEntity> getByInstanceId(long instanceId);

    /**
     * 根据请求id 获取task
     * @param requestId 请求id
     * @return 设备task
     */
    Optional<InstanceTaskEntity> getByRequestId(String requestId);

    /**
     *  根据状态获取集合
     * @param taskStatus 任务状态
     * @return 集合
     */
    List<InstanceTaskEntity> listByTaskStatus(String taskStatus, long processRecordId);

    /**
     * 根据设备key和状态获取任务
     * @param deviceKey 设备ket
     * @param taskStatus 状态
     * @return 任务
     */
    Optional<InstanceTaskEntity> getByDeviceKeyAndStatus(String deviceKey, String taskStatus);


    /**
     *  根据设备类型和状态获取任务列表
     * @param deviceType 设备类型
     * @param taskStatus 任务状态
     * @return 集合
     */
    List<InstanceTaskEntity> listByDeviceTypeAndTaskStatus(String deviceType, String taskStatus);

    /**
     * 根据设备key 获取任务
     * @param deviceKeys key集合
     * @return 设备任务集合
     */
    List<InstanceTaskEntity> listByDeviceTypesAndTaskStatus(List<String> deviceKeys, String taskStatus);

    /**
     * 根据实例id 修改 requestId
     * @param requestId 请求id
     * @param instanceId 实例id
     * @return 是否成功
     */
    boolean updateRequestIdAndTaskStatusByInstanceId(long instanceId,String requestId,String taskStatus );

    List<InstanceTaskEntity> listByTaskStatuses(List<String> taskStatuses);

    List<InstanceTaskEntity> listByInstanceId(Long instanceId);

    /**
     * 根据请求id 批量修改状态
     * @param requestIds requestIds
     * @param status 需要修改的状态
     * @return 是否成功
     */
    boolean batchUpdateStatusByRequestIdIn(List<String> requestIds,String status);

    /**
     * 根据requestId查询集合
     * @param requestId requestId
     * @return 集合
     */
    List<InstanceTaskEntity> listByRequestId(String requestId);


    List<InstanceTaskEntity> listByStatusAndPriority(String status, Integer priority);

    List<InstanceTaskEntity> listByInstanceIds(List<Long> instanceIds);


    /**
     * 根据实例id查询
     * <>
     * @param instanceIds id数组
     * @return  List<InstanceTaskView>
     */
    List<HephaestusInstanceTaskView> listInstanceTaskViewByInstanceIds(Set<Long> instanceIds);


    /**
     * 根据状态和历史组id查询
     * @param taskStatuses 状态集合
     * @param experimentGroupHistoryId 实验组历史id
     * @return List<InstanceTaskEntity>
     */
    List<InstanceTaskEntity> listByTaskStatusesAndHistoryId(List<String> taskStatuses, long processRecordId);

    /***
     * 根据实验组历史id 查询任务
     * @param processRecordId 实验组历史id
     * @return 任务集合
     */
    List<InstanceTaskEntity> listByHistoryId(long processRecordId);

}

