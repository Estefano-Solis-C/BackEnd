package com.test.backend.alerts.interfaces.rest.resources;

public record ResolveIncidentResource(
        String actionTaken,
        Long technicianId
) {
}
