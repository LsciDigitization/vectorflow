package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.enums.XcapStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceCapService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceCapManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InstanceCapManagerImpl implements InstanceCapManager {

    private final IInstanceCapService capService;

    private final IInstanceService instanceService;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

    /**
     * 开盖
     * 1. 根据instanceId查询InstanceCap，存在则不进行处理
     * 2. 查询instance实例，如果不存在则不执行开盖，打印日志
     * 3. 如果存在则执行开盖
     *     a. 设置实例id
     *     b. 设置开盖时间为当前时间
     *     c. 设置device_type、device_key
     *     d. 设置device_status 为 1已开盖
     *
     * @param instanceId 实例id
     * @param deviceType deviceType
     * @param deviceKey  设备key
     */
    public void openCap(long instanceId, DeviceType deviceType, String deviceKey) {

        if(StringUtils.isNotBlank(deviceKey)){
            // 查询instanceCap
            InstanceCapEntity instanceCap = capService.lambdaQuery().eq(InstanceCapEntity::getInstanceId, instanceId).one();

            if(Objects.isNull(instanceCap)){
                InstanceEntity instance = instanceService.getById(instanceId);
                // 判断实例是否存在 存在才执行开盖
                if (Objects.nonNull(instance)) {
                    InstanceCapEntity newInstanceCap = new InstanceCapEntity();
                    newInstanceCap.setInstanceId(instanceId);
                    newInstanceCap.setDeviceType(deviceType.getCode());
                    newInstanceCap.setDeviceKey(deviceKey);
                    newInstanceCap.setOpenCapTime(new Date());
                    newInstanceCap.setDeviceStatus(XcapStatusEnum.Open.getValue());
                    newInstanceCap.setProcessRecordId(instance.getProcessRecordId());
                    capService.save(newInstanceCap);
                    log.info("实例id:{},deviceKey:{},执行开盖", instanceId,deviceKey);
                }else{
                    log.info("实例id:{},不存在，不执行开盖", instanceId);
                }
            }else{
                log.info("实例id:{},已存在id为:{},的instanceCap，不执行开盖", instanceId,instanceCap.getId());
            }
        }else{
            log.info("实例id:{},deviceKey:{},deviceKey为null,，不执行开盖", instanceId,deviceKey);

        }


    }

    /**
     * 根据实例id关闭
     * @param instanceId 实例id
     */
    public void closeCap(long instanceId) {
        // 查询instanceCap
        InstanceCapEntity instanceCap = capService.lambdaQuery().eq(InstanceCapEntity::getInstanceId, instanceId).one();

        if(Objects.nonNull(instanceCap)){
            instanceCap.setDeviceStatus(XcapStatusEnum.Close.getValue());
            instanceCap.setCloseCapTime(new Date());
            capService.updateById(instanceCap);
            log.info("实例id:{},deviceKey:{},执行关盖", instanceId,instanceCap.getDeviceKey());

        }else{
            log.info("实例id:{},不存在，不执行关盖", instanceId);
        }
    }

    /**
     * 获取未开盖的设备
     * <p>
     *     SELECT * FROM hephaestus_instance_cap WHERE (device_status = ?)
     * </p>
     * <p>Parameters: 1(Integer)
     * @return List<InstanceCapEntity>
     */
    public List<InstanceCapEntity> getUncloseCaps() {
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();
        if(runningGroupOptional.isPresent()){
            List<InstanceCapEntity> list = capService.lambdaQuery()
                    .eq(InstanceCapEntity::getProcessRecordId,runningGroupOptional.get().getId())
                    .eq(InstanceCapEntity::getDeviceStatus, XcapStatusEnum.Open.getValue()).list();

            if(CollectionUtils.isNotEmpty(list)){
                return list;
            }
        }


        return List.of();
    }

    /**
     * 获取未关盖设备
     * <p>
     *     SELECT * FROM hephaestus_instance_cap WHERE (device_status = ? AND device_key = ?)
     * </p>
     * <p> Parameters: 1(Integer), IntelliXcap-1(String)
     * @param deviceKey deviceKey
     * @return  Optional<InstanceCapEntity>
     */
    public Optional<InstanceCapEntity> getUncloseCap(String deviceKey){

        if(StringUtils.isNotBlank(deviceKey)){
            Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();

            if(runningGroupOptional.isPresent()) {

                InstanceCapEntity instanceCap = capService.lambdaQuery()
                        .eq(InstanceCapEntity::getProcessRecordId,runningGroupOptional.get().getId())
                        .eq(InstanceCapEntity::getDeviceStatus, XcapStatusEnum.Open.getValue())
                        .eq(InstanceCapEntity::getDeviceKey, deviceKey)
                        .one();

                return Optional.ofNullable(instanceCap);
            }

        }

        return Optional.empty();
    }

}
