package com.accenture.chat.billing.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BillingRepository {

    private static final Map<String, Double> repository = new ConcurrentHashMap<>();

    static {
        repository.put("question-value", 100.0);
        repository.put("current-charge", 0.0);
        repository.put("rate", 0.0);
    }

    public Double findValueByKey(String key) {
        return repository.get(key);
    }

    public void saveValue(String key, Double value) {
        repository.put(key, value);
    }
}
