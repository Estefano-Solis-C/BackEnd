package com.test.backend.alerts.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CorrectiveActionResource(
        Long id,
        Long technicianId,
        String actionTaken,
        LocalDateTime performedAt
) {
}
