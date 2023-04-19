package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;

import java.util.List;
import java.util.Optional;

/**
 *  存储设备表
 *
 * @author xianming.hu
 */
public interface IHephaestusStorageService extends IService<HephaestusResourceStorage> {

    /**
     * 根据deviceKey 批量修改状态
     * set storage_status = storageStatus where device_key in (deviceKey)
     * @param deviceKeys deviceKeys数组
     * @param storageStatus 状态
     * @return 是否成功
     */
    boolean updateByDeviceKey(List<String> deviceKeys, String storageStatus);

    /**
     * 根据deviceKeys查询storage
     * where deviceKey in (deviceKeys) and  storageStatus !=  lock
     * @param deviceKeys  deviceKeys数组
     * @return 集合
     */
    List<HephaestusResourceStorage> listByDeviceKeys(List<String> deviceKeys);


    /**
     * 根据类型 修改指定条数 非锁定状态storage 锁定experimentGroupId
     * set storageStatus = 'lock',pool_id = poolId  where storage_status ='deviceKey' limit limit
     * @param deviceKey 设备key
     * @param poolId 实验组id
     * @param limit 指定条数
     * @return 是否成功
     */
    boolean updateByDeviceKeyLimit(String deviceKey,long poolId, int limit);

    /**
     *  根据池id 查询锁定storage
     * @param poolId 池id
     * @return 集合
     */
    List<HephaestusResourceStorage> listByPoolIdAndLock(long poolId);

    /**
     * 根据主键获取未被isDeleted的数据
     * @param id 主键ID
     * @return HephaestusStorage
     */
    Optional<HephaestusResourceStorage> getByIdForNoDeleted(long id);

    /**
     *  根据条件重置
     *  if(deviceKey != null){
     *      and device_key in (deviceKey)
     *  }
     *  if(poolId != null){
     *      and pool_id in(poolId)
     *  }
     * @param deviceKeys 设备key集合
     * @param poolIds 池id集合
     * @return 是否重置成功
     */
    boolean resetByDeviceKeyAndPoolIds(List<String> deviceKeys,List<Long> poolIds);

    /**
     * 根据实验组id 进行复位
     * @param experimentGroupId 实验组id‘
     * @return 是否复位成功
     */
    boolean resetByExperimentGroupId(long experimentGroupId);


    /**
     * 根据id数组 进行复位
     * @param ids  id数组
     * @return 是否复位成功
     */
    boolean resetByIds(List<Long> ids);

    /**

     * 根据实例id 修改状态
     * @param instanceId 实例id
     * @param storageStatus 状态
     * @return 是否成功
     */
    boolean updateStorageStatusByInstanceId(long instanceId,String storageStatus);

    /**
     * 根据资源类型删除
     * @param storageDeviceType deviceType
     * @param deviceKeys 设备key集合
     * @return 是否成功
     */
    boolean deleteByStorageDeviceType(String storageDeviceType,List<String> deviceKeys);

}

