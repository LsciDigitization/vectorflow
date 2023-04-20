package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceStatusEntity;
import com.mega.hephaestus.pms.data.model.service.IDeviceService;
import com.mega.hephaestus.pms.data.model.service.IResourceService;
import com.mega.hephaestus.pms.data.model.service.IProjectService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IResourceStatusService;
import com.mega.component.nuc.device.GenericDeviceType;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceStatus;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceResourceManagerImpl implements DeviceResourceManager {

    private final IResourceService deviceService;

    private final IDeviceService iDeviceService;

    private final IResourceStatusService resourceStatusService;

    private final IProjectService resourceGroupService;

    private final ExperimentProperties experimentProperties;

    // 定于全局
    private static List<ResourceEntity> resourceList = new CopyOnWriteArrayList<>();

    private static List<DeviceEntity> deviceList = new CopyOnWriteArrayList<>();

    // 流程记录
    private final ExperimentGroupHistoryManager processRecordManager;

    /**
     * 根据设备key 获取资源
     * <p>
     * <p>
     * WHERE (device_key = ? AND is_deleted = 0)
     * </p>
     *
     * @param deviceKey 设备key
     * @return Optional 资源
     */
    public Optional<DeviceResourceModel> getDeviceResource(String deviceKey) {
//        Optional<ResourceEntity> deviceOptional = deviceService.getByDeviceKey(deviceKey);

        List<DeviceResourceModel> allResources = getAllResources();
        Optional<DeviceResourceModel> deviceOptional = allResources.stream()
                .filter(v -> v.getDeviceKey().equals(deviceKey))
                .findFirst();

        Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
        if(runningGroup.isPresent()){
            Optional<ResourceStatusEntity> resourceStatusOptional = resourceStatusService.getByDeviceKey(deviceKey,runningGroup.get().getId());
            if (resourceStatusOptional.isPresent()) {
                ResourceStatusEntity resourceStatusEntity = resourceStatusOptional.get();
                deviceOptional.ifPresent(device -> device.setResourceStatus(DeviceResourceStatus.toEnum(resourceStatusEntity.getResourceStatus())));
            }
        }

        return deviceOptional;
    }



    /**
     * 获取所有空闲设备
     *
     * @return 空闲设备
     */
    public List<DeviceResourceModel> getIdleDevices() {

        List<DeviceResourceModel> allResources = getAllResources();

//        List<ResourceStatusEntity> allResourceStatus = resourceStatusService.list();

        return allResources.stream()
                .filter(v -> v.getResourceStatus().getCode().equals(DeviceResourceStatus.IDLE.getCode()))
                .collect(Collectors.toList());

//      List<ResourceEntity> HephaestusResources = deviceService.lambdaQuery()
//        .eq(ResourceEntity::getResourceStatus, DeviceResourceStatus.IDLE.getCode())
//        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
//        .list();
//    if (CollectionUtils.isEmpty(HephaestusResources)) {
//      return List.of();
//    }
//    return HephaestusResources.stream().map(this::deviceToResourceModel)
//        .collect(Collectors.toList());
    }

    /**
     * 获取所有运行中的设备
     *
     * @return 空闲设备
     */
    public List<DeviceResourceModel> getRunningDevices() {

        List<DeviceResourceModel> allResources = getAllResources();
        return allResources.stream()
                .filter(v -> v.getResourceStatus().getCode().equals(DeviceResourceStatus.RUNNING.getCode()))
                .collect(Collectors.toList());
//    List<ResourceEntity> HephaestusResources = deviceService.lambdaQuery()
//        .eq(ResourceEntity::getResourceStatus, DeviceResourceStatus.RUNNING.getCode())
//        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
//        .list();
//
//    if (CollectionUtils.isEmpty(HephaestusResources)) {
//      return List.of();
//    }
//    return HephaestusResources.stream().map(this::deviceToResourceModel)
//        .collect(Collectors.toList());
    }

    /**
     * 根据key 修改资源为idle
     *
     * @param deviceKey 资源key
     * @return 是否成功
     */
    public boolean idleDeviceResource(String deviceKey) {
        Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
        if(runningGroup.isPresent()){
            return resourceStatusService.updateOrInsertResourceStatus(deviceKey, DeviceResourceStatus.IDLE.getCode(),runningGroup.get().getId());
        }
        return false;
//        return deviceService.updateResourceStatus(deviceKey, DeviceResourceStatus.IDLE.getCode());
    }

    /**
     * 根据key 修改资源为running
     *
     * @param deviceKey 资源key
     * @return 是否成功
     */
    public boolean runningDeviceResource(String deviceKey) {
        Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
        if(runningGroup.isPresent()){
            return resourceStatusService.updateOrInsertResourceStatus(deviceKey, DeviceResourceStatus.RUNNING.getCode(),runningGroup.get().getId());
        }
        return false;
//        return deviceService.updateResourceStatus(deviceKey, DeviceResourceStatus.RUNNING.getCode());
    }


    /**
     * 获取所有设备资源
     *
     * @return 空闲设备
     */
    public List<DeviceResourceModel> getAllDevices() {
        return getAllResources();

//    List<ResourceEntity> HephaestusResources = deviceService.lambdaQuery()
//        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
//        .list();
//    if (CollectionUtils.isEmpty(HephaestusResources)) {
//      return List.of();
//    }
//    return HephaestusResources.stream().map(this::deviceToResourceModel)
//        .collect(Collectors.toList());
    }

    /**
     * 获取所有瓶颈资源
     * <p>
     * SELECT * from  hephaestus_device  WHERE (resource_bottleneck = true AND is_deleted = 0)
     * </p>
     *
     * @return 空闲设备
     */
    public List<DeviceResourceModel> getBottleneckDevices() {

        List<DeviceResourceModel> allResources = getAllResources();
        return allResources.stream()
                .filter(v -> v.getResourceBottleneck().equals(Boolean.TRUE))
                .collect(Collectors.toList());
//    List<ResourceEntity> HephaestusResources = deviceService.lambdaQuery()
//        .eq(ResourceEntity::getResourceBottleneck, Boolean.TRUE)
//        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
//        .list();
//    if (CollectionUtils.isEmpty(HephaestusResources)) {
//      return List.of();
//    }
//    return HephaestusResources.stream().map(this::deviceToResourceModel)
//        .collect(Collectors.toList());
    }

    public DeviceResourceModel deviceToResourceModel(ResourceEntity device) {
        DeviceResourceModel deviceResourceModel = new DeviceResourceModel();
        deviceResourceModel.setId(device.getId());
//        deviceResourceModel.setDeviceType(DeviceTypeEnum.toEnum(device.getDeviceType()));
        // @TODO 临时处理，后续需要修改
        deviceResourceModel.setDeviceType(new GenericDeviceType(device.getDeviceType(), device.getDeviceType()));

        deviceResourceModel.setDeviceKey(device.getDeviceKey());
        deviceResourceModel.setResourceStatus(DeviceResourceStatus.toEnum(device.getResourceStatus()));
        deviceResourceModel.setResourceBottleneck(device.getResourceBottleneck());
        String resourcePlateNumber = device.getResourcePlateNumber();
        if (StringUtils.hasText(resourcePlateNumber)) {
            String[] split = resourcePlateNumber.split(",");
            List<Integer> collect = Arrays.stream(split).map(Integer::valueOf)
                    .collect(Collectors.toList());
            deviceResourceModel.setResourcePlateNumber(collect);
        } else {
            deviceResourceModel.setResourcePlateNumber(List.of());
        }
        return deviceResourceModel;
    }


    /**
     * 根据资源组id 获取资源
     *
     * @param experimentType 实验类型
     * @return List<DeviceResourceModel> 资源
     */
    public List<DeviceResourceModel> getAllDevices(String experimentType) {
        List<DeviceResourceModel> allResources = getAllResources(experimentType);

        if (CollectionUtils.isEmpty(allResources)) {
            return List.of();
        }

        return allResources;
    }

    /**
     * 根据资源组id 获取资源(无资源状态)
     *
     * @param experimentType 实验类型
     * @return List<DeviceResourceModel> 资源
     */
    @Override
    public List<DeviceResourceModel> getAllDevicesNoStatus(String experimentType) {

        Optional<ProjectEntity> experimentTypeOptional = resourceGroupService.getByExperimentType(experimentType);

        if (experimentTypeOptional.isPresent()) {
            ProjectEntity projectEntity = experimentTypeOptional.get();
            if(CollectionUtils.isEmpty(resourceList)){
                List<ResourceEntity> list = deviceService.lambdaQuery()
                        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                        .eq(ResourceEntity::getProjectId, projectEntity.getId())
                        .list();
               return  list.stream().map(this::deviceToResourceModel).collect(Collectors.toList());

            }
            return resourceList.stream().map(this::deviceToResourceModel).collect(Collectors.toList());
        }
        return List.of();
    }


    //================== ResourceEntity ====================

    /**
     * 获取所有的资源
     *
     * @return ResourceEntity list
     */
    private List<DeviceResourceModel> getAllResources() {
        String type = experimentProperties.getCode();
        return getAllResources(type);

//        List<ResourceEntity> list = deviceService.lambdaQuery()
//                .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
//                .list();
//        return new CopyOnWriteArrayList<>(list);
    }

    /**
     * 根据实验类型获取所有的资源
     *
     * @param experimentType 实验类型
     * @return ResourceEntity list
     */
    private List<DeviceResourceModel> getAllResources(String experimentType) {
        Optional<ProjectEntity> experimentTypeOptional = resourceGroupService.getByExperimentType(experimentType);

        if (experimentTypeOptional.isPresent()) {
            ProjectEntity projectEntity = experimentTypeOptional.get();
            if(CollectionUtils.isEmpty(resourceList)){
                resourceList =  deviceService.lambdaQuery()
                        .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                        .eq(ResourceEntity::getProjectId, projectEntity.getId())
                        .list();
            }

            if(CollectionUtils.isEmpty(deviceList)){
                deviceList = iDeviceService.listByProjectId(projectEntity.getId());
            }
             // 拿到所有状态
            List<ResourceStatusEntity> resourceStatusEntityList = new ArrayList<>();
            try{
                Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
                if(runningGroup.isPresent()){
                    resourceStatusEntityList = resourceStatusService.listByProcessRecordId(runningGroup.get().getId());
                }

            }catch (Exception e){
                log.error("获取资源状态失败");
            }

//            List<ResourceStatusEntity>
            // 状态按照deviceKey 进行分组
            Map<String, List<ResourceStatusEntity>> resourceStatusMap = resourceStatusEntityList.stream().collect(Collectors.groupingBy(ResourceStatusEntity::getDeviceKey));

            // 资源表中的key 存在 状态 则将状态表中的状态 赋值给资源表的状态值  否则 设置为空闲
            Map<String, List<DeviceEntity>> deviceMap = deviceList.stream().collect(Collectors.groupingBy(DeviceEntity::getDeviceKey));

            return resourceList.stream().map(resource -> {
                DeviceResourceModel model = deviceToResourceModel(resource);


                String deviceKey = resource.getDeviceKey();
                if (resourceStatusMap.containsKey(deviceKey)) {
                    List<ResourceStatusEntity> resourceStatusEntities = resourceStatusMap.get(deviceKey);
                    if (CollectionUtils.isNotEmpty(resourceStatusEntities)) {
                        model.setResourceStatus(DeviceResourceStatus.toEnum(resourceStatusEntities.get(0).getResourceStatus()));
                    } else {
                        model.setResourceStatus(DeviceResourceStatus.IDLE);
                    }

                } else {
                    model.setResourceStatus(DeviceResourceStatus.IDLE);

                }

                if (deviceMap.containsKey(deviceKey)) {
                    List<DeviceEntity> deviceEntities = deviceMap.get(deviceKey);
                    if (CollectionUtils.isNotEmpty(deviceEntities)) {
                        model.setUrl(deviceEntities.get(0).getServerAddress());
                        model.setCallbackUrl(deviceEntities.get(0).getCallbackAddress());

                    }
                }

                return model;
            }).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        }

        return List.of();
    }

}
