package com.test.backend.alerts.domain.model.commands;

public record ApplyEscalationCommand(Long incidentId, String nextRole) {
}
