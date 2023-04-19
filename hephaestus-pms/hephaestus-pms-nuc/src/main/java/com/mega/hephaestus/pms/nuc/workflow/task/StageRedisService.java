package com.mega.hephaestus.pms.nuc.workflow.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StageRedisService implements StorageTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void put(String key, String hashKey, Object data) {
        this.redisTemplate.opsForHash().put(key, hashKey, data);
    }

    public Object get(String key, String hashKey) {
        return this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public void delete(String key, String... hasKey) {
        this.redisTemplate.opsForHash().delete(key, hasKey);

    }

    // 是否存在key
    public boolean hasKey(String key, String... hasKey) {
        return this.redisTemplate.opsForHash().hasKey(key, hasKey);
    }

//    /**
//     * @param command
//     * @return
//     */
//    public boolean check(String command) {
//        return existKey(command);
//    }
//
//
//    protected String getKey(String command) {
//        return this.deviceId + "_" + command;
//    }
}
