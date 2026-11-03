package com.test.backend.alerts.infrastructure.adapters;

import com.test.backend.alerts.domain.services.ThresholdProjectionPort;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ThresholdProjectionAdapter implements ThresholdProjectionPort {

    public record Thresholds(Double minThreshold, Double maxThreshold) {}

    private final ConcurrentHashMap<Long, Thresholds> projectionCache = new ConcurrentHashMap<>();

    @Override
    public void updateThresholds(Long laboratoryId, Double minThreshold, Double maxThreshold) {
        if (laboratoryId != null) {
            projectionCache.put(laboratoryId, new Thresholds(minThreshold, maxThreshold));
        }
    }

    public Thresholds getThresholds(Long laboratoryId) {
        return projectionCache.get(laboratoryId);
    }
}
