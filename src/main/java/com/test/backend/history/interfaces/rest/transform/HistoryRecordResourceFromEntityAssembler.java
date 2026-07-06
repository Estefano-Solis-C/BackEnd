package com.test.backend.history.interfaces.rest.transform;

import com.test.backend.history.domain.model.aggregates.HistoryRecord;
import com.test.backend.history.interfaces.rest.resources.HistoryRecordResource;

public class HistoryRecordResourceFromEntityAssembler {
    public static HistoryRecordResource toResourceFromEntity(HistoryRecord entity) {
        if (entity == null) return null;
        String labName = entity.getLaboratory() != null ? entity.getLaboratory().getName() : "";
        return new HistoryRecordResource(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getOccurredAt() != null ? entity.getOccurredAt().atZone(java.time.ZoneOffset.UTC).toInstant() : null,
            labName,
            entity.getEventType(),
            entity.getSeverity(),
            entity.getStatus()
        );
    }
}
