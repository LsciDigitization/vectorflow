package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import com.mega.hephaestus.pms.data.model.enums.StorageStatusEnum;
import com.mega.hephaestus.pms.data.model.mapper.StorageMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusExperimentGroupPoolService;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 存储设备表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HephaestusStorageServiceImpl extends
        ServiceImpl<StorageMapper, HephaestusResourceStorage> implements IHephaestusStorageService {

    private final IHephaestusExperimentGroupPoolService groupPoolService;

    /**
     * 根据deviceKey 批量修改状态
     * set storage_status = storageStatus where device_key in (deviceKey)
     *
     * @param deviceKeys    deviceKeys数组
     * @param storageStatus 状态
     * @return 是否成功
     */
    @Override
    public boolean updateByDeviceKey(List<String> deviceKeys, String storageStatus) {
        if (CollectionUtils.isEmpty(deviceKeys)) {
            return false;
        }
        HephaestusResourceStorage storage = new HephaestusResourceStorage();
        storage.setStorageStatus(storageStatus);
        return lambdaUpdate().in(HephaestusResourceStorage::getDeviceKey, deviceKeys)
                .update(storage);
    }

    /**
     * 根据deviceKeys查询storage
     * where deviceKey in (deviceKeys) and  storageStatus !=  lock
     *
     * @param deviceKeys deviceKeys数组
     * @return 集合
     */
    @Override
    public List<HephaestusResourceStorage> listByDeviceKeys(List<String> deviceKeys) {
        List<HephaestusResourceStorage> list = lambdaQuery().in(HephaestusResourceStorage::getDeviceKey, deviceKeys)
                .ne(HephaestusResourceStorage::getStorageStatus, StorageStatusEnum.LOCK.getValue())
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据类型 修改指定条数 非锁定状态storage 锁定
     *
     * @param deviceKey devicekey
     * @param limit     指定条数
     * @return 是否成功
     */
    @Override
    public boolean updateByDeviceKeyLimit(String deviceKey, long poolId, int limit) {
        HephaestusResourceStorage storage = new HephaestusResourceStorage();
        storage.setStorageStatus(StorageStatusEnum.LOCK.getValue());
        storage.setPoolId(poolId);
        return lambdaUpdate().eq(HephaestusResourceStorage::getDeviceKey, deviceKey)
                .ne(HephaestusResourceStorage::getStorageStatus, StorageStatusEnum.LOCK.getValue())
                .last(" limit " + String.valueOf(limit))
                .update(storage);
    }

    /**
     * 根据池id 查询锁定storage
     *
     * @param poolId 池id
     * @return 集合
     */
    @Override
    public List<HephaestusResourceStorage> listByPoolIdAndLock(long poolId) {
        List<HephaestusResourceStorage> list = lambdaQuery()
                .eq(HephaestusResourceStorage::getPoolId, poolId)
                .eq(HephaestusResourceStorage::getStorageStatus, StorageStatusEnum.LOCK)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public Optional<HephaestusResourceStorage> getByIdForNoDeleted(long id) {
        HephaestusResourceStorage one = lambdaQuery()
                .eq(HephaestusResourceStorage::getId, id)
                .eq(HephaestusResourceStorage::getIsDeleted, BooleanEnum.NO)
                .one();
        return Optional.ofNullable(one);
    }

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
    @Override
    public boolean resetByDeviceKeyAndPoolIds(List<String> deviceKeys, List<Long> poolIds) {

        return lambdaUpdate().in(CollectionUtils.isNotEmpty(deviceKeys), HephaestusResourceStorage::getDeviceKey, deviceKeys)
                .in(CollectionUtils.isNotEmpty(poolIds), HephaestusResourceStorage::getPoolId, poolIds)
                .set(HephaestusResourceStorage::getPoolId, null)
                .set(HephaestusResourceStorage::getStorageStatus, StorageStatusEnum.IDLE.getValue())
                .update();
    }

    /**
     * 根据实验组id 进行复位
     *
     * @param experimentGroupId 实验组id
     * @return 是否复位成功
     */
    @Override
    public boolean resetByExperimentGroupId(long experimentGroupId) {
        List<HephaestusExperimentGroupPool> groupPoolList = groupPoolService.listByExperimentGroupId(experimentGroupId);
        List<Long> poolIds = groupPoolList.stream().map(HephaestusExperimentGroupPool::getId).collect(Collectors.toList());
        return resetByDeviceKeyAndPoolIds(null, poolIds);
    }

    /**
     * 根据id数组 进行复位
     *
     * @param ids id数组
     * @return 是否复位成功
     */
    @Override
    public boolean resetByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        HephaestusResourceStorage storage = new HephaestusResourceStorage();
        storage.setPoolId(null);
        storage.setStorageStatus(StorageStatusEnum.IDLE.getValue());
        return lambdaUpdate().in(HephaestusResourceStorage::getId, ids)
                .update(storage);
    }


    /**
     * 根据实例id 修改状态
     *
     * @param instanceId           实例id
     * @param storageStatus 状态
     * @return 是否成功
     */
    @Override
    public boolean updateStorageStatusByInstanceId(long instanceId, String storageStatus) {

        return lambdaUpdate()
                .set(HephaestusResourceStorage::getPoolId, null)
                .set(HephaestusResourceStorage::getStorageStatus, storageStatus)
                .update();
    }

    /**
     * 根据资源类型删除
     *
     * @param storageDeviceType deviceType
     * @param deviceKeys        设备key集合
     * @return 是否成功
     */
    @Override
    public boolean deleteByStorageDeviceType(String storageDeviceType, List<String> deviceKeys) {
        HephaestusResourceStorage storage = new HephaestusResourceStorage();
        storage.setIsDeleted(BooleanEnum.YES);
        return lambdaUpdate().eq(HephaestusResourceStorage::getStorageDeviceType, storageDeviceType)
                .in(CollectionUtils.isNotEmpty(deviceKeys), HephaestusResourceStorage::getDeviceKey,deviceKeys)
                .update(storage);
    }


}
