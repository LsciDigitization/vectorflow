package com.mega.hephaestus.pms.workflow.device.devicetask;


import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.component.sortedset.AbstractSortedSet;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

public class DeviceTaskSortedSet extends AbstractSortedSet<DeviceTaskEntity> {

    // 设备类型
    private final DeviceType deviceType;

    // 设备key，唯一标识
    private final String deviceKey;

    // redis key前缀
    private final static String redisKeyPrefix = "DeviceTask:";

    public DeviceTaskSortedSet(DeviceType deviceType, String deviceKey, RedisTemplate<String, DeviceTaskEntity> redisTemplate) {
        super(redisTemplate);
        this.redisKey = redisKeyPrefix + deviceKey;

        this.deviceType = deviceType;
        this.deviceKey = deviceKey;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    @Override
    public DeviceTaskEntity[] toArray() {
        return toList().toArray(DeviceTaskEntity[]::new);
    }

    public Map<String, DeviceTaskEntity> toMap() {
        Map<String, DeviceTaskEntity> entryMap = new HashMap<>();

        Set<ZSetOperations.TypedTuple<DeviceTaskEntity>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(redisKey, 0, -1);
        if (typedTuples != null) {
            typedTuples.forEach(tuple -> {
                DeviceTaskEntity value = tuple.getValue();
                if (value != null) {
                    value.setTaskParameterMap(null);
                    String key = value.getRequestId() + "-" + Objects.requireNonNull(tuple.getScore()).toString();
                    entryMap.put(key, value);
                }
            });
        }
        return entryMap;
    }

}
