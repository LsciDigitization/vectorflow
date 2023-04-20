package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.component.nuc.plate.GenericPlateType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.manager.plan.ResourcePlateTypeManager;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workstart.GenericWorkStartPlate;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartPool;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class WorkStartConfiguration extends BaseConfiguration<WorkStartRegister> {

    private final Map<String, WorkStartRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private WorkStartPool workStartPool;
    @Resource
    private WorkBusDaemonResource workBusDaemonResource;
    @Resource
    private WorkflowServiceFactory workflowServiceFactory;
    @Resource
    private ExperimentProperties experimentProperties;
    @Resource
    private ResourcePlateTypeManager resourcePlateTypeManager;

    /**
     * 添加资源板组
     */
    private void addPlateTypes() {
        String type = experimentProperties.getCode();
        List<LabwarePlateTypeEntity> allPlates = resourcePlateTypeManager.getAllPlates(type);
        allPlates.forEach(v -> {
            PlateType plateType = GenericPlateType.toEnum(v.getPlateKey());
            deviceTypeCallableMap.put(plateType.getCode(), new GenericWorkStartPlate(workBusDaemonResource, plateType));
        });
    }

    private void registerWorkStartServices() {
        List<WorkStartRegister> workStartRegisters = workflowServiceFactory.loadWorkStartRegisterServices();

        for (WorkStartRegister workStartRegister : workStartRegisters) {
            workStartRegister.setWorkBusDaemonResource(workBusDaemonResource);
            deviceTypeCallableMap.put(workStartRegister.plateType().getCode(), workStartRegister);
        }
    }

    private void populateWorkStartPool() {
        deviceTypeCallableMap.forEach(workStartPool::add);
    }

    @Override
    protected void registerServices() {
        // 添加资源板组
        addPlateTypes();
        registerWorkStartServices();
    }

    @Override
    protected void populatePool() {
        populateWorkStartPool();
    }
}
