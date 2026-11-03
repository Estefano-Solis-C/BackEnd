package com.test.backend.alerts.interfaces.rest.transform;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.interfaces.rest.resources.CorrectiveActionResource;
import com.test.backend.alerts.interfaces.rest.resources.IncidentResource;

public class IncidentResourceFromEntityAssembler {

    public static IncidentResource toResourceFromEntity(Incident entity) {
        if (entity == null) {
            return null;
        }

        CorrectiveActionResource actionResource = null;
        if (entity.getCorrectiveAction() != null) {
            var action = entity.getCorrectiveAction();
            actionResource = new CorrectiveActionResource(
                    action.getId(),
                    action.getTechnicianId(),
                    action.getActionTaken(),
                    action.getPerformedAt()
            );
        }

        return new IncidentResource(
                entity.getId(),
                entity.getLaboratory() != null ? entity.getLaboratory().getId() : null,
                entity.getLaboratory() != null ? entity.getLaboratory().getName() : null,
                entity.getTitle(),
                entity.getDescription(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getOpenedAt(),
                entity.getResolvedAt(),
                entity.getEscalationMaxMins(),
                entity.getEscalationNextRole(),
                actionResource
        );
    }
}
