package com.test.backend.history.interfaces.rest.resources;

public record HistoryRecordResource(
    Long id,
    String name,
    String description,
    java.time.Instant occurredAt,
    String lab,
    String eventType,
    String severity,
    String status
) {}
