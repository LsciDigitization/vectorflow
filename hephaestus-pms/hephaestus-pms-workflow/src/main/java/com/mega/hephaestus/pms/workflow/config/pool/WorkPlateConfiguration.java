package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.component.nuc.plate.GenericPlateType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ResourcePlateTypeManager;
import com.mega.hephaestus.pms.workflow.work.workconsumer.WorkPlateGenericConsumer;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlatePool;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class WorkPlateConfiguration extends BaseConfiguration<WorkPlateRegister> {

    private final Map<String, WorkPlateRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private WorkPlatePool workPlatePool;

    @Resource
    private InstanceLabwareManager instanceLabwareManager;
    @Resource
    private ResourcePlateTypeManager resourcePlateTypeManager;
    @Resource
    private ExperimentProperties experimentProperties;
    @Resource
    private WorkflowServiceFactory workflowServiceFactory;

    /**
     * 添加资源板组
     */
    private void addPlateTypes() {
        String type = experimentProperties.getCode();
        List<LabwarePlateTypeEntity> allPlates = resourcePlateTypeManager.getAllPlates(type);
        allPlates.forEach(v -> {
            PlateType poolType = GenericPlateType.toEnum(v.getPlateKey());
            deviceTypeCallableMap.put(poolType.getCode(), new WorkPlateGenericConsumer(poolType, instanceLabwareManager));
        });
    }

    private void registerWorkPlateServices() {
        List<WorkPlateRegister> workPlateRegisters = workflowServiceFactory.loadWorkPlateRegisterServices();

        for (WorkPlateRegister workPlateRegister : workPlateRegisters) {
            deviceTypeCallableMap.put(workPlateRegister.plateType().getCode(), workPlateRegister);
        }
    }

    private void populateWorkPlatePool() {
        deviceTypeCallableMap.forEach((key, register) -> {
            register.setInstancePlateManager(instanceLabwareManager);
            workPlatePool.add(key, register);
        });
    }

    @Override
    protected void registerServices() {
        // 添加资源板组
        addPlateTypes();
        registerWorkPlateServices();
    }

    @Override
    protected void populatePool() {
        populateWorkPlatePool();
    }
}
