package com.mega.hephaestus.pms.workflow.config;

import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckRegister;
import com.mega.hephaestus.pms.workflow.device.devicebus.BusDaemonRegister;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceScheduleRegister;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypeRegister;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartRegister;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/29 16:20
 */
public class WorkflowServiceFactory implements ApplicationContextAware {

    private static final List<WorkStartRegister> workStartRegisters = SpringFactoriesLoader.loadFactories(WorkStartRegister.class,
            Thread.currentThread().getContextClassLoader());
    private static final List<DeviceBottleneckRegister> deviceBottleneckRegisters = SpringFactoriesLoader.loadFactories(DeviceBottleneckRegister.class,
            Thread.currentThread().getContextClassLoader());

    private static final List<BusDaemonRegister> busDaemonRegisters = SpringFactoriesLoader.loadFactories(BusDaemonRegister.class,
            Thread.currentThread().getContextClassLoader());

    private static final List<DeviceScheduleRegister> deviceScheduleRegisters = SpringFactoriesLoader.loadFactories(DeviceScheduleRegister.class,
            Thread.currentThread().getContextClassLoader());

    private static final List<TaskTypeRegister> taskTypeRegisters = SpringFactoriesLoader.loadFactories(TaskTypeRegister.class,
            Thread.currentThread().getContextClassLoader());

    private static final List<WorkPlateRegister> workPlateRegisters = SpringFactoriesLoader.loadFactories(WorkPlateRegister.class,
            Thread.currentThread().getContextClassLoader());


    private ApplicationContext applicationContext;

    public WorkflowServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<WorkStartRegister> loadWorkStartRegisterServices() {
        List<WorkStartRegister> services = new ArrayList<>();
        for (WorkStartRegister workStartRegister : workStartRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(workStartRegister);
            services.add(workStartRegister);
        }
        return services;
    }

    public List<DeviceBottleneckRegister> loadDeviceBottleneckRegisterServices() {
        List<DeviceBottleneckRegister> services = new ArrayList<>();
        for (DeviceBottleneckRegister deviceBottleneckRegister : deviceBottleneckRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(deviceBottleneckRegister);
            services.add(deviceBottleneckRegister);
        }
        return services;
    }

    public List<BusDaemonRegister> loadBusDaemonRegisterServices() {
        List<BusDaemonRegister> services = new ArrayList<>();
        for (BusDaemonRegister busDaemonRegister : busDaemonRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(busDaemonRegister);
            services.add(busDaemonRegister);
        }
        return services;
    }

    public List<DeviceScheduleRegister> loadDeviceScheduleRegisterServices() {
        List<DeviceScheduleRegister> services = new ArrayList<>();
        for (DeviceScheduleRegister deviceScheduleRegister : deviceScheduleRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(deviceScheduleRegister);
            services.add(deviceScheduleRegister);
        }
        return services;
    }

    public List<TaskTypeRegister> loadTaskTypeRegisterServices() {
        List<TaskTypeRegister> services = new ArrayList<>();
        for (TaskTypeRegister taskTypeRegister : taskTypeRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(taskTypeRegister);
            services.add(taskTypeRegister);
        }
        return services;
    }

    public List<WorkPlateRegister> loadWorkPlateRegisterServices() {
        List<WorkPlateRegister> services = new ArrayList<>();
        for (WorkPlateRegister workPlateRegister : workPlateRegisters) {
            // 使用 ApplicationContext 对实现类进行自动注入
            applicationContext.getAutowireCapableBeanFactory().autowireBean(workPlateRegister);
            services.add(workPlateRegister);
        }
        return services;
    }

}