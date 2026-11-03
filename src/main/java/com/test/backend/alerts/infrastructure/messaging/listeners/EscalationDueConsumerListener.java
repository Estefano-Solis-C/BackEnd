package com.test.backend.alerts.infrastructure.messaging.listeners;

import com.test.backend.alerts.domain.model.commands.ApplyEscalationCommand;
import com.test.backend.alerts.domain.model.events.EscalationDueEvent;
import com.test.backend.alerts.domain.services.IncidentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EscalationDueConsumerListener {

    private final IncidentCommandService incidentCommandService;

    public EscalationDueConsumerListener(IncidentCommandService incidentCommandService) {
        this.incidentCommandService = incidentCommandService;
    }

    @EventListener
    public void on(EscalationDueEvent event) {
        incidentCommandService.handle(new ApplyEscalationCommand(event.incidentId(), event.targetRole()));
    }
}
