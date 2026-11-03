package com.test.backend.alerts.interfaces.rest.resources;

public record CreateIncidentResource(
        Long laboratoryId,
        String title,
        String description,
        String severity,
        Integer escalationMaxMins,
        String escalationNextRole
) {
}
