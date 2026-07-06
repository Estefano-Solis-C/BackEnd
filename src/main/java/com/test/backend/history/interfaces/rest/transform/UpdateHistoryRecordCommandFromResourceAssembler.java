package com.test.backend.history.interfaces.rest.transform;

import com.test.backend.history.domain.model.commands.UpdateHistoryRecordCommand;
import com.test.backend.history.interfaces.rest.resources.UpdateHistoryRecordResource;

public class UpdateHistoryRecordCommandFromResourceAssembler {
    public static UpdateHistoryRecordCommand toCommandFromResource(Long id, UpdateHistoryRecordResource resource) {
        if (resource == null) return null;
        return new UpdateHistoryRecordCommand(
            id,
            resource.name(),
            resource.description(),
            resource.occurredAt() != null ? java.time.LocalDateTime.ofInstant(resource.occurredAt(), java.time.ZoneOffset.UTC) : null,
            resource.lab(),
            resource.eventType(),
            resource.severity(),
            resource.status()
        );
    }
}
