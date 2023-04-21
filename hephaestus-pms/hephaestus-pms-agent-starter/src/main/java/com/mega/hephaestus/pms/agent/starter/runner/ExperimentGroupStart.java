package com.mega.hephaestus.pms.agent.starter.runner;

import com.mega.hephaestus.pms.agent.starter.config.ProjectProperties;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/15 13:38
 */
@Component
public class ExperimentGroupStart {

    @Autowired
    private ProjectProperties projectProperties;

    @Resource
    private ExperimentGroupManager experimentGroupManager;

    @Scheduled(initialDelay = 2000, fixedDelay = -1, fixedRate = Integer.MAX_VALUE)
    public void run() {
        try {
            if (projectProperties.isAutoStart()) {
                Long experimentGroupId = projectProperties.getExperimentGroupId();
                if (Objects.nonNull(experimentGroupId)) {
                    boolean start = experimentGroupManager.startNew(experimentGroupId);
                    if (start) {
                        System.out.println("实验启动成功！！！");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
