package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceDaemonCallable;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.*;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class DeviceDaemonConfiguration extends BaseConfiguration<DeviceDaemonCallable> {
    private final Map<String, DeviceDaemonCallable> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private DeviceDaemonPool deviceDaemonPool;
    @Resource
    private WorkProperties workProperties;
    @Resource
    private ExperimentProperties experimentProperties;
    @Resource
    private DeviceResourceManager deviceResourceManager;
    @Resource
    private GenericDaemonResource genericDaemonResource;

    /**
     * 从资源组中加载资源守护线程
     */
    private void addDeviceDaemon() {
        String type = experimentProperties.getCode();
        log.info("当前实验类型 {}", type);
        List<DeviceResourceModel> allDevices = deviceResourceManager.getAllDevicesNoStatus(type);
        allDevices.forEach(v -> {
            deviceTypeCallableMap.put(v.getDeviceType().getCode(), () -> new GenericDaemon(genericDaemonResource, v.getDeviceType()));
        });
    }

    private void startDeviceDaemonThreads() {
        // 开启开关，启动设备守护线程
        if (workProperties.isEnabled()) {
            String type = experimentProperties.getCode();
            List<DeviceResourceModel> allDevices = deviceResourceManager.getAllDevicesNoStatus(type);
            allDevices.forEach(this::createThread);
        }
    }

    @Override
    protected void registerServices() {
        // 从资源组中加载资源守护线程
        addDeviceDaemon();
    }

    @Override
    protected void populatePool() {
        startDeviceDaemonThreads();
    }

    private void createThread(DeviceResourceModel deviceResourceModel) {
        DeviceType deviceType = deviceResourceModel.getDeviceType();

        if (deviceTypeCallableMap.containsKey(deviceType.getCode())) {
            try {
                Runnable runnable = deviceTypeCallableMap.get(deviceType.getCode()).call();
                Thread thread = new Thread(runnable, deviceResourceModel.getDeviceKey());
                thread.setDaemon(true);
                // 创建完线程不直接start
                deviceDaemonPool.add(deviceResourceModel.getDeviceKey(), thread);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Scheduled(initialDelay = 5000, fixedDelay = -1, fixedRate = Integer.MAX_VALUE)
    public void delayThreadStart() {
        startAllThreadsInPool();
    }

    private void startAllThreadsInPool() {
        Map<String, Thread> allThread = deviceDaemonPool.all();
        allThread.forEach((s, thread) -> {
            thread.start();
        });
    }


}
