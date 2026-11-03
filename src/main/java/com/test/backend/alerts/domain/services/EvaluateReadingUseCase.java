package com.test.backend.alerts.domain.services;

import com.test.backend.alerts.domain.model.aggregates.Incident;

import java.util.Optional;

public interface EvaluateReadingUseCase {
    Optional<Incident> evaluate(Long laboratoryId, Double temperature, Double minThreshold, Double maxThreshold, String labName);
}
