package com.mega.hephaestus.pms.workflow.config.register;

import com.mega.hephaestus.pms.workflow.device.devicebus.BusDaemonRegister;
import com.mega.hephaestus.pms.workflow.device.devicebus.BusEnum;
import com.mega.hephaestus.pms.workflow.device.devicebus.BusRegister;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemon;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;

import java.util.concurrent.Callable;

public class WorkBusRegister implements BusDaemonRegister {

    private WorkBusDaemonResource daemonResource;

    public WorkBusRegister() {
    }

    public WorkBusRegister(WorkBusDaemonResource daemonResource) {
        this.daemonResource = daemonResource;
    }

    public void setDaemonResource(WorkBusDaemonResource daemonResource) {
        this.daemonResource = daemonResource;
    }

    @Override
    public BusEnum busType() {
        return BusEnum.WorkBus;
    }

    @Override
    public BusRegister createBusDaemon() {
        return () -> new WorkBusDaemon(daemonResource);
    }
}
