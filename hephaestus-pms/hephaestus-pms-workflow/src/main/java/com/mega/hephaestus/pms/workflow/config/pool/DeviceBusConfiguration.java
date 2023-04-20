package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.device.devicebus.*;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class DeviceBusConfiguration extends BaseConfiguration<BusRegister> {

    private final Map<String, BusRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private WorkProperties workProperties;
    @Resource
    private DeviceBusPool deviceBusPool;
    @Resource
    private WorkBusDaemonResource workBusDaemonResource;
    @Resource
    private DeviceBusDaemonResource deviceBusDaemonResource;
    @Resource
    private WorkflowServiceFactory workflowServiceFactory;

    private void loadBusDaemons() {
        List<BusDaemonRegister> busDaemonRegisters = workflowServiceFactory.loadBusDaemonRegisterServices();

        for (BusDaemonRegister busDaemonRegister : busDaemonRegisters) {
            setDaemonResource(busDaemonRegister);
            BusRegister busDaemon = busDaemonRegister.createBusDaemon();
            deviceTypeCallableMap.put(busDaemonRegister.busType().name(), busDaemon);
        }
    }

    private void setDaemonResource(BusDaemonRegister busDaemonRegister) {
        if (busDaemonRegister.busType().equals(BusEnum.DeviceBus)) {
            busDaemonRegister.setDaemonResource(deviceBusDaemonResource);
        } else if (busDaemonRegister.busType().equals(BusEnum.WorkBus)) {
            busDaemonRegister.setDaemonResource(workBusDaemonResource);
        }
    }

    private void initializeDeviceBusPoolThreads() {
        deviceTypeCallableMap.forEach((key, daemon) -> {
            Runnable runnable = getRunnable(daemon);
            Thread thread = new Thread(runnable, key);
            thread.setDaemon(true);
            deviceBusPool.add(key, thread);
        });
    }

    private Runnable getRunnable(BusRegister daemon) {
        try {
            Runnable runnable = daemon.call();
            return runnable != null ? runnable : () -> {};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void registerServices() {
        loadBusDaemons();
    }

    @Override
    protected void populatePool() {
        if (workProperties.isEnabled()) {
            initializeDeviceBusPoolThreads();
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = -1, fixedRate = Integer.MAX_VALUE)
    public void delayThreadStart() {
        deviceBusPool.all().forEach((s, thread) -> thread.start());
    }

}
