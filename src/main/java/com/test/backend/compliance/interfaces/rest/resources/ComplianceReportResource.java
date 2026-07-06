package com.test.backend.compliance.interfaces.rest.resources;

public record ComplianceReportResource(
    Long id,
    String name,
    String type,
    String period,
    String size,
    java.time.LocalDateTime generatedAt,
    String status
) {}
