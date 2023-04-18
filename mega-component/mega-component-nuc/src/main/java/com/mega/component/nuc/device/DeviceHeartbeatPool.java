package com.mega.component.nuc.device;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceHeartbeatPool {


    private final Map<DeviceType, Runnable> pool = new ConcurrentHashMap<>();


    /**
     * 添加心跳任务
     * @param type 设备类型
     * @param runnable 任务执行
     */
    public void addHeartbeatTask(DeviceType type, Runnable runnable) {
        pool.put(type, runnable);
    }

    /**
     * 执行单个任务
     * @param type 设备类型
     * @return 返回true，执行完成，返回false，未执行
     */
    public boolean doRunTask(DeviceType type) {
        if (pool.containsKey(type)) {
            Runnable runnable = pool.get(type);
            runnable.run();
            return true;
        }
        return false;
    }

    /**
     * 批量运行任务
     */
    public void batchRunTask() {
        pool.values().forEach(Runnable::run);
    }


}
