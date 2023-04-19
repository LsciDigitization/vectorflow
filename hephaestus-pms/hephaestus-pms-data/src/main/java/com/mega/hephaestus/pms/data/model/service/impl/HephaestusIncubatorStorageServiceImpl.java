package com.mega.hephaestus.pms.data.model.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusIncubatorStorage;
import com.mega.hephaestus.pms.data.model.enums.StorageModelStatusEnum;
import com.mega.hephaestus.pms.data.model.mapper.IncubatorStorageMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusIncubatorStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Co2培育箱存放模型表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@Deprecated(since = "20221115")
public class HephaestusIncubatorStorageServiceImpl extends
        ServiceImpl<IncubatorStorageMapper, HephaestusIncubatorStorage> implements IHephaestusIncubatorStorageService {

    @Autowired
    private IncubatorStorageMapper incubatorStorageMapper;

    public Optional<HephaestusIncubatorStorage> getIncubatorStorageByKey(String plateKey) {
        HephaestusIncubatorStorage one = lambdaQuery()
                .eq(HephaestusIncubatorStorage::getPlateKey, plateKey)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 分配一个保温箱资源
     *
     * @return HephaestusIncubatorStorage
     */
    public HephaestusIncubatorStorage allocate() {
        HephaestusIncubatorStorage incubatorStorage = lambdaQuery()
                .eq(HephaestusIncubatorStorage::getStorageStatus, StorageModelStatusEnum.FREE)
                .last("limit 1")
                .one();
        if (incubatorStorage != null) {
//            incubatorStorage.setInstanceId(instanceId);
            incubatorStorage.setStorageStatus(StorageModelStatusEnum.OCCUPY);
            this.updateById(incubatorStorage);
        }
        return incubatorStorage;
    }

    /**
     * 根据id, 释放保温箱资源
     *
     * @param id 保温箱ID
     * @return 返回是否成功
     */
    public boolean free(Long id) {
        HephaestusIncubatorStorage incubatorStorage = this.getById(id);
        if (incubatorStorage != null) {
            incubatorStorage.setInstanceId(0L);
            incubatorStorage.setStorageStatus(StorageModelStatusEnum.FREE);
        }
        return this.updateById(incubatorStorage);
    }


    /**
     * 根据key, 释放保温箱资源
     *
     * @param plateKey 保温箱key
     * @return 返回是否成功
     */
    public boolean free(String plateKey) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Optional<HephaestusIncubatorStorage> incubatorStorageOptional = getIncubatorStorageByKey(plateKey);
        incubatorStorageOptional.ifPresentOrElse(incubatorStorage -> {
            incubatorStorage.setInstanceId(0L);
            incubatorStorage.setStorageStatus(StorageModelStatusEnum.FREE);
            this.updateById(incubatorStorage);
            atomicBoolean.set(true);
        }, null);

        return atomicBoolean.get();
    }

}
