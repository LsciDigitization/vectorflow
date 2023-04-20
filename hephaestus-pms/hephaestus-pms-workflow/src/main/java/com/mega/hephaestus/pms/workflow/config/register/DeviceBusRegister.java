package com.mega.hephaestus.pms.workflow.config.register;

import com.mega.hephaestus.pms.workflow.device.devicebus.*;

import java.util.concurrent.Callable;


public class DeviceBusRegister implements BusDaemonRegister {

    private DeviceBusDaemonResource daemonResource;

    public DeviceBusRegister() {
    }

    public DeviceBusRegister(DeviceBusDaemonResource daemonResource) {
        this.daemonResource = daemonResource;
    }

    public void setDaemonResource(DeviceBusDaemonResource daemonResource) {
        this.daemonResource = daemonResource;
    }

    @Override
    public BusEnum busType() {
        return BusEnum.DeviceBus;
    }

    @Override
    public BusRegister createBusDaemon() {
        return () -> new DeviceBusDaemon(daemonResource);
    }
}
