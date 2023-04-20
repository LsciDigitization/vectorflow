package com.mega.hephaestus.pms.workflow.device.devicebus;

import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;

import java.util.concurrent.Callable;

public interface BusDaemonRegister {

    BusEnum busType();

    BusRegister createBusDaemon();

    default void setDaemonResource(DeviceBusDaemonResource daemonResource) {}

    default void setDaemonResource(WorkBusDaemonResource daemonResource) {}

}
