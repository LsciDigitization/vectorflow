package com.mega.hephaestus.pms.workflow.device.devicelock;

import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskEntity;

/**
 * 设备锁接口
 */
public interface DeviceLock {

    /**
     * 申请设备锁
     *
     * @param anEntry   业务实体
     * @param priority  优先级
     * @return 返回执行结果
     */
    boolean lock(DeviceTaskEntity anEntry, Long priority);


    /**
     * 解除设备锁，取消申请
     *
     * @param anEntry  业务实体
     * @return 返回执行结果
     */
    boolean unlock(DeviceTaskEntity anEntry);

    /**
     * 获取设备锁，拿到执行权限
     *
     * @param anEntry  业务实体
     * @return 返回执行结果
     */
    boolean acquireLock(DeviceTaskEntity anEntry);

}
