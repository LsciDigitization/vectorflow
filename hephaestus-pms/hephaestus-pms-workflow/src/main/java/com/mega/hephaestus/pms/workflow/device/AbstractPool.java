package com.mega.hephaestus.pms.workflow.device;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPool<T> {

    protected final Map<String, T> pool = new ConcurrentHashMap<>();

    public void add(String key, T object) {
        pool.put(key, object);
    }

    public boolean has(String key) {
        return pool.containsKey(key);
    }

    public Optional<T> get(String key) {
        return Optional.ofNullable(pool.get(key));
    }

    public void remove(String deviceKey) {
        pool.remove(deviceKey);
    }

    public int count() {
        return pool.size();
    }

    public Map<String, T> all() {
        return pool;
    }

    public Set<String> allKeys() {
        return pool.keySet();
    }

    public Collection<T> allValues() {
        return pool.values();
    }

    public void clear() {
        pool.clear();
    }

}
