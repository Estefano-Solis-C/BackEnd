package com.test.backend.alerts.domain.model.events;

public record ReadingAcceptedEvent(
        Long laboratoryId,
        Double temperature,
        Double minThreshold,
        Double maxThreshold,
        String labName
) {
}
