package com.test.backend.alerts.domain.model.events;

public record ThresholdsChangedEvent(
        Long laboratoryId,
        Double minThreshold,
        Double maxThreshold
) {
}
