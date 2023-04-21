package com.mega.hephaestus.pms.agent.dashboard.domain.manager;

import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:30
 */
@Component
@Slf4j
public class ProcessRecordDTOManager{

    @Autowired
    private IProcessRecordService processRecordService;



    // 当前应用名
    @Value("${spring.application.name}")
    private String applicationName;


    /**
     * 获取最后一次 如果有运行的返回运行的 没有运行的返回 最后一个完成的
     *
     * @return 当前
     */

    public Optional<ProcessRecordEntity> getLast() {
        return processRecordService.getLastByName(applicationName);

    }

}
