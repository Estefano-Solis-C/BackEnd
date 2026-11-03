package com.test.backend.alerts.domain.model.commands;

public record ResolveIncidentCommand(Long incidentId, String actionTaken, Long technicianId) {
}
