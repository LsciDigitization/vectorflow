package com.mega.hephaestus.pms.data.model.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusIncubatorStorage;

import java.util.Optional;

/**
 * Co2培育箱存放模型表
 *
 * @author xianming.hu
 */
@Deprecated(since = "20221115")
public interface IHephaestusIncubatorStorageService extends IService<HephaestusIncubatorStorage> {

    Optional<HephaestusIncubatorStorage> getIncubatorStorageByKey(String key);
    HephaestusIncubatorStorage allocate();

    /**
     * 根据id, 释放保温箱资源
     *
     * @param id 保温箱ID
     * @return 返回是否成功
     */
    boolean free(Long id);

    /**
     * 根据key, 释放保温箱资源
     *
     * @param plateKey 保温箱key
     * @return 返回是否成功
     */
    boolean free(String plateKey);

}


