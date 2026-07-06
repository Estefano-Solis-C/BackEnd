package com.test.backend.history.domain.model.commands;

public record UpdateHistoryRecordCommand(
    Long id,
    String name,
    String description,
    java.time.LocalDateTime occurredAt,
    String lab,
    String eventType,
    String severity,
    String status
) {}