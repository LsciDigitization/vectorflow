package com.mega.hephaestus.pms.workflow.component.sortedset;

import org.springframework.data.redis.core.RedisTemplate;

public class StringSortedSet extends AbstractSortedSet<String> {

    // redis key前缀
    private final String redisKeyPrefix = "String:";

    public StringSortedSet(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }


    @Override
    public String[] toArray() {
        return toList().toArray(String[]::new);
    }

}
