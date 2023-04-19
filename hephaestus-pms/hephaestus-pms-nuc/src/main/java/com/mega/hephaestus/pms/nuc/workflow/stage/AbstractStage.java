package com.mega.hephaestus.pms.nuc.workflow.stage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public abstract class AbstractStage<T> {

    private ThreadPoolExecutor deviceLockExecutor;

    public void setDeviceLockExecutor(ThreadPoolExecutor deviceLockExecutor) {
        this.deviceLockExecutor = deviceLockExecutor;
    }

    // 运行任务
    public abstract void run(T runParameter);

//    /**
//     * 重试获取设备锁，获取失败，释放所有设备锁
//     * @param instanceId 任务ID
//     * @param retryTimeout 重试超时时间
//     * @param devices 设备列表
//     */
//    public void retryLockAll(String instanceId, int retryTimeout, AbstractDevice...devices) {
//        final int length = devices.length;
//        final CountDownLatch latch = new CountDownLatch(length);
//
//        try {
//            // 并行执行
//            Arrays.stream(devices).forEach(device -> {
//                deviceLockExecutor.execute(() -> {
//                    if (device != null) {
//                        // 申请锁
//                        boolean lock = device.lock(instanceId);
//                        // 查检是否获得锁
//                        while ( ! device.checkLock(instanceId)) {
//                            log.info("InstanceId {} {} lock获取锁失败", instanceId, device.getDeviceType());
//                            try {
//                                TimeUnit.SECONDS.sleep(1);
//                            } catch (InterruptedException e) {
//                                // ignore
//                            }
//                        }
//                        latch.countDown();
//                    } else {
//                        latch.countDown();
//                    }
//                });
//            });
//
//            // 获取锁超时1800秒
//            boolean await = latch.await(1800, TimeUnit.SECONDS);
//            if (! await) {
//                log.info("InstanceId {} await timeout.", instanceId);
//                unlockAll(instanceId, devices);
//                throw new StageException(String.format("InstanceId %s await timeout.", instanceId));
//            }
//        } catch (InterruptedException e) {
//            log.info("InstanceId {} InterruptedException {}", instanceId, e.getClass().getName());
//            unlockAll(instanceId, devices);
//            throw new StageException(String.format("InstanceId %s InterruptedException %s", instanceId, e.getClass().getName()), e);
//        }
//    }

//    /**
//     * 重试获取设备锁，获取失败，释放所有设备锁
//     * @param instanceId 任务ID
//     * @param retryTimeout 重试超时时间
//     * @param devices 设备列表
//     */
//    public void retryLockAll(String instanceId, int retryTimeout, Consumer<AbstractDevice> deviceConsumer, AbstractDevice...devices) {
//        final int length = devices.length;
//        final CountDownLatch latch = new CountDownLatch(length);
//
//        try {
//            // 并行执行
//            Arrays.stream(devices).forEach(device -> {
//                deviceLockExecutor.execute(() -> {
//                    if (device != null) {
//                        // 申请锁
//                        boolean lock = device.lock(instanceId);
//                        // 查检是否获得锁
//                        while ( ! device.checkLock(instanceId)) {
//                            log.info("InstanceId {} {} lock获取锁失败", instanceId, device.getDeviceType());
//                            try {
//                                TimeUnit.SECONDS.sleep(1);
//                            } catch (InterruptedException e) {
//                                // ignore
//                            }
//                        }
//                        latch.countDown();
//                    } else {
//                        latch.countDown();
//                    }
//                });
//            });
//
//            // 获取锁超时1800秒
//            boolean await = latch.await(1800, TimeUnit.SECONDS);
//            if (! await) {
//                log.info("InstanceId {} await timeout.", instanceId);
//                unlockAll(instanceId, devices);
//                throw new StageException(String.format("InstanceId %s await timeout.", instanceId));
//            }
//        } catch (InterruptedException e) {
//            log.info("InstanceId {} InterruptedException {}", instanceId, e.getClass().getName());
//            unlockAll(instanceId, devices);
//            throw new StageException(String.format("InstanceId %s InterruptedException %s", instanceId, e.getClass().getName()), e);
//        }
//    }

//    /**
//     * 获取设备锁，获取失败，释放所有设备锁
//     * @param instanceId 任务ID
//     * @param devices 设备列表
//     */
//    public void lockAll(String instanceId, AbstractDevice ...devices) {
//        final int length = devices.length;
//        final CountDownLatch latch = new CountDownLatch(length);
//
//        ExecutorService service = Executors.newFixedThreadPool(length);
//        Arrays.stream(devices).forEach(device -> {
//            service.submit(() -> {
//                if (device != null) {
//                    boolean lock = device.lock(instanceId);
//                    if (! lock) {
//                        log.info("InstanceId {} lock获取锁失败", instanceId);
//                        Thread.currentThread().interrupt();
//                    } else {
//                        latch.countDown();
//                    }
//                } else {
//                    latch.countDown();
//                }
//            });
//        });
//
//        try {
//            boolean await = latch.await(10, TimeUnit.SECONDS);
//            if (! await) {
//                log.info("InstanceId {} await timeout.", instanceId);
//                unlockAll(instanceId, devices);
//                throw new StageException(String.format("InstanceId %s await timeout.", instanceId));
//            }
//        } catch (InterruptedException e) {
//            log.info("InstanceId {} InterruptedException {}", instanceId, e);
//            unlockAll(instanceId, devices);
//            throw new StageException(e);
//        }
//    }

//    /**
//     * 释放所有设备锁
//     * @param instanceId 任务ID
//     * @param devices 设备列表
//     */
//    public void unlockAll(String instanceId, AbstractDevice ...devices) {
//        Arrays.stream(devices).forEach(device -> {
//            if (device != null) {
//                device.unlock(instanceId);
//            }
//        });
//    }

//    public void unlockAll(String instanceId, Consumer<AbstractDevice> deviceConsumer, AbstractDevice ...devices) {
//        Arrays.stream(devices).forEach(device -> {
//            if (device != null) {
//                device.unlock(instanceId);
//                deviceConsumer.accept(device);
//            }
//        });
//    }

}
