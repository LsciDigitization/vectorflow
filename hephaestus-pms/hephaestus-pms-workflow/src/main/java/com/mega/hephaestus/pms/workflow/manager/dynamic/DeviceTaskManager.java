package com.mega.hephaestus.pms.workflow.manager.dynamic;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import java.util.*;

/**
 * 设备task
 */
public interface DeviceTaskManager {

  /**
   * 创建task
   *
   * @param instanceTask task任务
   * @param deviceKey 设备key
   * @return 创建后的deviceTask对象
   */
  Optional<ResourceTaskEntity> createTask(InstanceTaskEntity instanceTask,
                                          String deviceKey);


  /**
   * task 开始进行
   *
   * @param requestId task请求id
   * @return 是否成功
   */
  ResourceTaskEntity runningTask(String requestId);


  /**
   * task 开始进行
   *
   * @param deviceKey 设备key
   * @param requestId task请求id
   * @return 是否成功
   */
  ResourceTaskEntity runningTask(String requestId, String deviceKey);


  /**
   * 完成
   *
   * @param requestId 实例id
   */
  ResourceTaskEntity finishedTask(String requestId);

  /**
   * 失败
   *
   * @param requestId 实例id
   * @param errorMessage 错误信息
   */
  ResourceTaskEntity failedTask(String requestId, String errorMessage);

  /**
   * 检查task 状态
   *
   * @param requestId 实例id
   * @return true 完成，false未完成，异常:失败或者找不到
   */
  boolean checkTaskFinished(String requestId);

  /**
   * 根据请求id 获取设备task
   *
   * @param requestId 请求id
   * @return 设备task
   */
  Optional<ResourceTaskEntity> getDeviceTask(String requestId);


  /**
   * 根据设备key范围获取可用设备key
   *
   * @param deviceKeyRange 设备范围
   * @return 可用设备key
   */
  Set<String> getDeviceKey(String deviceKeyRange);


  /**
   * 根据requestIds 查询已经结束或者失败的task
   *
   * @param requestIds 请求ids
   * @return 任务集合
   */
  List<ResourceTaskEntity> listFinishedAndFailedByRequestIds(Set<String> requestIds);


  /**
   * 根据设备获取正在运行的task 如果存在多个，则返回第一个
   *
   * @param deviceKey 设备key
   * @return HephaestusDeviceTask Optional
   */
  Optional<ResourceTaskEntity> getRunningTask(String deviceKey);

}
