package com.mega.hephaestus.pms.data.runtime.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;

import java.util.Optional;


/**
 * 实验组历史service
 *
 * @author xianming.hu
 */
public interface IProcessRecordService extends IService<ProcessRecordEntity> {



    /**
     * 获取最后一次 如果有运行的返回运行的 没有运行的返回 最后一个完成的
     * @return
     */
    Optional<ProcessRecordEntity> getLast();

    Optional<ProcessRecordEntity> getLastByName(String applicationName);
}

