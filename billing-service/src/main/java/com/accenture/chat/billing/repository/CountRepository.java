package com.accenture.chat.billing.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CountRepository {

    private static final Map<String, Integer> repository = new ConcurrentHashMap<>();

    public Integer findValueByKey(String key) {
        return repository.get(key);
    }

    public void saveValue(String key, Integer value) {
        repository.put(key, value);
    }
}