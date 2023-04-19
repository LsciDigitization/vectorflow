package com.mega.hephaestus.pms.data.model.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStorageModel;

import java.util.List;
import java.util.Optional;

/**
 * 存放模型表
 *
 * @author xianming.hu
 */
@Deprecated(since = "20221115")
public interface IHephaestusStorageModelService extends IService<HephaestusStorageModel> {

//    /**
//     * 根据storageId查询  NucStorageModel
//     * @param storageId id
//     * @return NucStorageModel  NucStorageModel
//     */
//    Optional<NucStorageModel> getNucStorage(Long storageId);

    /**
     * 查询
     * @param nucId nucId
     * @param nestGroupId nestGroupId
     * @param nest nest
     * @return HephaestusStorageModel  模型对象
     */
    Optional<HephaestusStorageModel> getHephaestusStorage(String nucId,String nestGroupId,Integer nest);

    /**
     *根据设备id查询 模型List
     * @param deviceId 设备id
     * @return List<HephaestusStorageModel>  模型集合
     */
    List<HephaestusStorageModel> listByDeviceId(Long deviceId);
}

