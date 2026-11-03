package com.test.backend.alerts.domain.services;

public interface ThresholdProjectionPort {
    void updateThresholds(Long laboratoryId, Double minThreshold, Double maxThreshold);
}
