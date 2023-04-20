package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.GenericDevice;
import com.mega.hephaestus.pms.nuc.manager.AbstractDeviceManager;
import com.mega.hephaestus.pms.nuc.manager.GenericDeviceManager;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
@Deprecated(since = "20230322")
public class DeviceNowLock {

    private final DeviceClientPool deviceClientPool;
    private final ThreadPoolExecutor deviceLockExecutor;
    private final StringRedisTemplate redisTemplate;

    // 申请设备锁
    @Nullable
    public AbstractDevice tryAcquireLock(String instanceId, ResourceTaskEntity task) {
        Set<String> deviceKeySet = deviceKeySet(task);

        Set<GenericDevice> deviceLockSet = deviceKeySet.stream()
                .map(v -> {
                    Optional<GenericDeviceManager> deviceManagerOptional = deviceClientPool.get(v);
                    return deviceManagerOptional.map(AbstractDeviceManager::getDevice).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 遍历获取设备锁
        // 设备锁为空，忽略处理
        if (deviceLockSet.size() == 0) {
            return null;
        }

        // 临时锁加锁
        return tryAcquireLock(instanceId, deviceLockSet.toArray(new AbstractDevice[]{}));
    }


    // 申请设备锁
    @Nullable
    public AbstractDevice tryAcquireLock(String instanceId, HephaestusStageTask task) {
        Set<String> deviceKeySet = deviceKeySet(task);

        Set<GenericDevice> deviceLockSet = deviceKeySet.stream()
                .map(v -> {
                    Optional<GenericDeviceManager> deviceManagerOptional = deviceClientPool.get(v);
                    return deviceManagerOptional.map(AbstractDeviceManager::getDevice).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 遍历获取设备锁
        // 设备锁为空，忽略处理
        if (deviceLockSet.size() == 0) {
            return null;
        }

        // 临时锁加锁
        return tryAcquireLock(instanceId, deviceLockSet.toArray(new AbstractDevice[]{}));
    }

    /**
     * 重试获取设备锁，获取失败，释放所有设备锁
     *
     * @param instanceId 任务ID
     * @param deviceKeys 设备Key列表
     */
    public AbstractDevice tryAcquireLock(String instanceId, String... deviceKeys) {
        Set<GenericDevice> deviceLockSet = Arrays.stream(deviceKeys)
                .map(v -> {
                    Optional<GenericDeviceManager> deviceManagerOptional = deviceClientPool.get(v);
                    return deviceManagerOptional.map(AbstractDeviceManager::getDevice).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 遍历获取设备锁
        // 设备锁为空，忽略处理
        if (deviceLockSet.size() == 0) {
            return null;
        }

        // 临时锁加锁
        return tryAcquireLock(instanceId, deviceLockSet.toArray(new AbstractDevice[]{}));
    }

    /**
     * 重试获取设备锁，获取失败，释放所有设备锁
     *
     * @param instanceId 任务ID
     * @param devices    设备列表
     */
    public AbstractDevice tryAcquireLock(String instanceId, AbstractDevice... devices) {
        return null;
//        // 并行执行
//        return Arrays.stream(devices).parallel()
//                .filter(Objects::nonNull)
//                .map(device -> {
//                    final Future<AbstractDevice> submit = deviceLockExecutor.submit(() -> {
//                        try {
//                            // 申请锁
//                            boolean lock = device.lock(instanceId);
//                            log.debug("InstanceId {} {} lock 申请锁 {}", instanceId, device.getDeviceKey(), lock);
//
//                            DeviceTaskLockKey deviceTaskLockKey = new DeviceTaskLockKey(device.getDeviceType(), instanceId);
//                            // 查检是否获得锁
//                            while (!(device.checkLock(instanceId) &&
//                                    Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(deviceTaskLockKey.key(), instanceId)))
//                            ) {
//                                log.info("InstanceId {} {} lock获取锁失败", instanceId, device.getDeviceKey());
//
//                                try {
//                                    TimeUnit.SECONDS.sleep(5);
//                                } catch (InterruptedException e) {
//                                    // ignore
//                                }
//
//                                if (Objects.nonNull(redisTemplate.opsForValue().get(deviceTaskLockKey.key()))) {
//                                    return null;
//                                }
//                            }
//
//                            log.info("InstanceId {} {} lock获取锁成功", instanceId, device.getDeviceKey());
//                            return device;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                    });
//                    try {
//                        return submit.get();
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull)
//                .findFirst()
//                .orElse(null);
    }

    /**
     * 释放所有设备锁
     *
     * @param instanceId 任务ID
     * @param devices    设备列表
     */
    public void unlockAll(String instanceId, AbstractDevice... devices) {
//        Arrays.stream(devices).forEach(device -> {
//            if (device != null) {
////                device.unlock(instanceId);
//                // 解锁
//                DeviceTaskLockKey deviceTaskLockKey = new DeviceTaskLockKey(device.getDeviceType(), instanceId);
//                redisTemplate.delete(deviceTaskLockKey.key());
//            }
//        });
    }

    public void unlockAll(String instanceId, Consumer<AbstractDevice> deviceConsumer, AbstractDevice... devices) {
//        Arrays.stream(devices).forEach(device -> {
//            if (device != null) {
////                device.unlock(instanceId);
//                deviceConsumer.accept(device);
//                // 解锁
//                DeviceTaskLockKey deviceTaskLockKey = new DeviceTaskLockKey(device.getDeviceType(), instanceId);
//                redisTemplate.delete(deviceTaskLockKey.key());
//            }
//        });
    }


    private Set<String> deviceKeySet(ResourceTaskEntity task) {
        //todo 字段调整
//        String[] strings = deviceKeys(task.getDeviceKeyRange());
//        return Arrays.stream(strings).collect(Collectors.toSet());
        return Set.of();
    }

    private Set<String> deviceKeySet(HephaestusStageTask task) {
        String[] strings = deviceKeys(task.getDeviceKey());
        return Arrays.stream(strings).collect(Collectors.toSet());
    }

    private String[] deviceKeys(String deviceKeys) {
        return deviceKeys.split(",");
    }

    static class WaitNotifyLock {
        // 是否继续等待
        static final AtomicBoolean flag = new AtomicBoolean(true);
    }

}
