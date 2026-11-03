package com.test.backend.alerts.interfaces.rest.resources;

import java.time.LocalDateTime;

public record IncidentResource(
        Long id,
        Long laboratoryId,
        String laboratoryName,
        String title,
        String description,
        String severity,
        String status,
        LocalDateTime openedAt,
        LocalDateTime resolvedAt,
        Integer escalationMaxMins,
        String escalationNextRole,
        CorrectiveActionResource correctiveAction
) {
}
