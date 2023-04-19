package com.mega.hephaestus.pms.data.model.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStorageModel;
import com.mega.hephaestus.pms.data.model.mapper.StorageModelMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStorageModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 存放模型表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@Deprecated(since = "20221115")
public class HephaestusStorageModelServiceImpl extends
        ServiceImpl<StorageModelMapper, HephaestusStorageModel> implements IHephaestusStorageModelService {

    @Autowired
    private StorageModelMapper hephaestusStorageModelMapper;

//    public Optional<NucStorageModel> getNucStorage(Long storageId) {
//        NucStorageModel nucStorage = hephaestusStorageModelMapper.getNucStorage(storageId);
//        return Optional.ofNullable(nucStorage);
//    }

    public Optional<HephaestusStorageModel> getHephaestusStorage(String nucId, String nestGroupId, Integer nest) {
        HephaestusStorageModel hephaestusStorage = hephaestusStorageModelMapper.getHephaestusStorage(nucId, nestGroupId, nest);
        return Optional.ofNullable(hephaestusStorage);
    }

    /**
     * 根据设备id查询 模型List
     *
     * @param deviceId 设备id
     * @return List<HephaestusStorageModel>  模型集合
     */
    @Override
    public List<HephaestusStorageModel> listByDeviceId(Long deviceId) {
        List<HephaestusStorageModel> list = lambdaQuery().eq(HephaestusStorageModel::getDeviceId, deviceId).list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }
}
