package com.mega.hephaestus.pms.workflow.daemon.centrifuge;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DeviceDaemonRegister;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.GenericDeviceDaemonProcess;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
@Deprecated(since = "20230331")
public class CentrifugeDaemon implements Runnable, DeviceDaemonRegister {

    private final CentrifugeDaemonResource centrifugeDaemonResource;

    public CentrifugeDaemon(CentrifugeDaemonResource centrifugeDaemonResource) {
        this.centrifugeDaemonResource = centrifugeDaemonResource;
    }

    @Override
    public void run() {
        log.info("{} running...", this.getClass().getSimpleName());

        while (true) {
            try {
                log.debug("{} {}", Thread.currentThread().getName(), LocalTime.now());

                String deviceKey = Thread.currentThread().getName();

                GenericDeviceDaemonProcess genericDeviceDaemonProcess = new GenericDeviceDaemonProcess(centrifugeDaemonResource, deviceKey);
                genericDeviceDaemonProcess.run();

            } catch (Exception e) {
                log.error("{} Daemon run exception: {}", this.getClass().getSimpleName(), e.getMessage());
            }

            // 休息一会
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public DeviceType deviceType() {
        return DeviceTypeEnum.Centrifuge;
    }
}
