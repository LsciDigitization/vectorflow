package com.mega.hephaestus.pms.workflow.device.devicedaemon;

import com.mega.component.nuc.device.DeviceType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public class GenericDaemon implements Runnable, DeviceDaemonRegister {

    private final GenericDaemonResource genericDaemonResource;

    private final DeviceType deviceType;

    public GenericDaemon(GenericDaemonResource genericDaemonResource, DeviceType deviceType) {
        this.genericDaemonResource = genericDaemonResource;
        this.deviceType = deviceType;
    }

    @Override
    public void run() {
        log.info("{} running...", deviceType.getCode());

        while (true) {
            try {
                log.debug("{} {}", Thread.currentThread().getName(), LocalTime.now());

                String deviceKey = Thread.currentThread().getName();

                GenericDeviceDaemonProcess genericDeviceDaemonProcess = new GenericDeviceDaemonProcess(genericDaemonResource, deviceKey);
                genericDeviceDaemonProcess.run();

            } catch (Exception e) {
                log.error("{} Daemon run exception: {}", deviceType.getCode(), e.getMessage());
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
        return deviceType;
    }
}
