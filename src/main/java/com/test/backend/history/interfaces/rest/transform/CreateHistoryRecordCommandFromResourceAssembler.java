package com.test.backend.history.interfaces.rest.transform;

import com.test.backend.history.domain.model.commands.CreateHistoryRecordCommand;
import com.test.backend.history.interfaces.rest.resources.CreateHistoryRecordResource;

public class CreateHistoryRecordCommandFromResourceAssembler {
    public static CreateHistoryRecordCommand toCommandFromResource(CreateHistoryRecordResource resource) {
        if (resource == null) return null;
        return new CreateHistoryRecordCommand(
            resource.name(),
            resource.description(),
            resource.occurredAt() != null ? java.time.LocalDateTime.ofInstant(resource.occurredAt(), java.time.ZoneOffset.UTC) : java.time.LocalDateTime.now(java.time.ZoneOffset.UTC),
            resource.lab(),
            resource.eventType(),
            resource.severity(),
            resource.status() != null ? resource.status() : "Logged"
        );
    }
}
