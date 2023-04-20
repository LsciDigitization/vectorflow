package com.mega.hephaestus.pms.workflow.component.sortedset;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractSortedSet<T> implements SetInterface<T> {

    protected final RedisTemplate<String, T> redisTemplate;

    // redis key
    protected String redisKey;

    // redis key前缀
    protected final static String redisKeyPrefix = "DeviceTask:";

    public AbstractSortedSet(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisKey = redisKeyPrefix;
    }

    public String getRedisKey() {
        return redisKey;
    }

    @Override
    public long getCurrentSize() {
        return Optional
                .ofNullable(redisTemplate.opsForZSet().size(redisKey))
                .orElse(0L);
    }

    @Override
    public boolean isEmpty() {
        return getCurrentSize() == 0;
    }

    @Override
    public boolean add(T newEntry) {
        return add(newEntry, 100);
    }

    public boolean add(T newEntry, double score) {
        return Optional
                .ofNullable(redisTemplate.opsForZSet().add(redisKey, newEntry, score))
                .orElse(false);
    }

    @Override
    public boolean remove(T anEntry) {
        long removeResult = Optional
                .ofNullable(redisTemplate.opsForZSet().remove(redisKey, anEntry))
                .orElse(0L);
        return removeResult > 0L;
    }

    @Override
    public Optional<T> remove() {
        T anEntry = redisTemplate.opsForZSet().randomMember(redisKey);
        Long removeResult = redisTemplate.opsForZSet().remove(redisKey, anEntry);
        if (removeResult != null) {
            return removeResult > 0L ? Optional.of(anEntry) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> removeLast() {
        Set<T> range = redisTemplate.opsForZSet().reverseRange(redisKey, 0, 1);
        if (range != null) {
            Long removeResult = Optional
                    .ofNullable(redisTemplate.opsForZSet().remove(redisKey, range.toArray()))
                    .orElse(0L);
            return removeResult > 0L ? range.stream().map(o -> (T) o).findFirst() : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> removeHead() {
        Set<T> range = redisTemplate.opsForZSet().range(redisKey, 0, 1);
        if (range != null) {
            Long removeResult = Optional
                    .ofNullable(redisTemplate.opsForZSet().remove(redisKey, range.toArray()))
                    .orElse(0L);
            return removeResult > 0L ? range.stream().map(o -> (T) o).findFirst() : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> get() {
        T anEntry = redisTemplate.opsForZSet().randomMember(redisKey);
        return Optional.of(anEntry);
    }

    @Override
    public Optional<T> getLast() {
        Set<T> range = redisTemplate.opsForZSet().reverseRange(redisKey, 0, 1);
        if (range != null) {
            return range.stream().map(o -> (T) o).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> getHead() {
        Set<T> range = redisTemplate.opsForZSet().range(redisKey, 0, 1);
        if (range != null) {
            return range.stream().map(o -> (T) o).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        redisTemplate.opsForZSet().removeRange(redisKey, 1L, -1);
    }

    @Override
    public boolean contains(T anEntry) {
        Double score = redisTemplate.opsForZSet().score(redisKey, anEntry);
        return score != null && score >= 0;
    }

    public List<T> toList() {
        Set<T> range = redisTemplate.opsForZSet().range(redisKey, 0, -1);
        if (range != null) {
            return range.stream().map(o -> (T) o).collect(Collectors.toList());
        }
        return List.of();
    }

    public Map<String, T> toMap() {
        Map<String, T> entryMap = new HashMap<>();

        Set<ZSetOperations.TypedTuple<T>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(redisKey, 0, -1);
        if (typedTuples != null) {
            typedTuples.forEach(tuple -> {
                T value = tuple.getValue();
                if (value != null) {
                    String key = Objects.requireNonNull(tuple.getScore()).toString();
                    entryMap.put(key, value);
                }
            });
        }
        return entryMap;
    }

}

