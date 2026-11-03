package com.test.backend.alerts.interfaces.rest.transform;

import com.test.backend.alerts.domain.model.commands.CreateIncidentCommand;
import com.test.backend.alerts.interfaces.rest.resources.CreateIncidentResource;

public class CreateIncidentCommandFromResourceAssembler {

    public static CreateIncidentCommand toCommandFromResource(CreateIncidentResource resource) {
        return new CreateIncidentCommand(
                resource.laboratoryId(),
                resource.title(),
                resource.description(),
                resource.severity(),
                resource.escalationMaxMins(),
                resource.escalationNextRole()
        );
    }
}
