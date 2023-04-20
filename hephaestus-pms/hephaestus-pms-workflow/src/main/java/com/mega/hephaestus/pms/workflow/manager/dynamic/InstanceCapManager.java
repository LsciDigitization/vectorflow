package com.mega.hephaestus.pms.workflow.manager.dynamic;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import com.mega.component.nuc.device.DeviceType;

import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 */
public interface InstanceCapManager {

    /**
     * 开盖
     * 1. 根据instanceId查询InstanceCap，存在则不进行处理
     * 2. 查询instance实例，如果不存在则不执行开盖，打印日志
     * 3. 如果存在则执行开盖
     *     a. 设置实例id
     *     b. 设置开盖时间为当前时间
     *     c. 设置device_type、device_key
     *     d. 设置device_status 为 1已开盖
     *
     * @param instanceId 实例id
     * @param deviceType deviceType
     * @param deviceKey  设备key
     */
    void openCap(long instanceId, DeviceType deviceType, String deviceKey);

    /**
     * 根据实例id关闭
     * @param instanceId 实例id
     */
    void closeCap(long instanceId);

    /**
     * 获取未开盖的设备
     * <p>
     *     SELECT * FROM hephaestus_instance_cap WHERE (device_status = ?)
     * </p>
     * <p>Parameters: 1(Integer)
     * @return List<InstanceCapEntity>
     */
    List<InstanceCapEntity> getUncloseCaps();

    /**
     * 获取未关盖设备
     * <p>
     *     SELECT * FROM hephaestus_instance_cap WHERE (device_status = ? AND device_key = ?)
     * </p>
     * <p> Parameters: 1(Integer), IntelliXcap-1(String)
     * @param deviceKey deviceKey
     * @return  Optional<InstanceCapEntity>
     */
    Optional<InstanceCapEntity> getUncloseCap(String deviceKey);

}
