package com.test.backend.alerts.domain.model.events;

public record EscalationDueEvent(
        Long incidentId,
        String targetRole
) {
}
