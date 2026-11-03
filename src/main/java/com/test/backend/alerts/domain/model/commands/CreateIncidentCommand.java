package com.test.backend.alerts.domain.model.commands;

public record CreateIncidentCommand(
        Long laboratoryId,
        String title,
        String description,
        String severity,
        Integer escalationMaxMins,
        String escalationNextRole
) {
}
