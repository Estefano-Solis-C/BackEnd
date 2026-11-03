package com.test.backend.alerts.application.internal.commandservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.commands.AcknowledgeIncidentCommand;
import com.test.backend.alerts.domain.model.commands.ApplyEscalationCommand;
import com.test.backend.alerts.domain.model.commands.CreateIncidentCommand;
import com.test.backend.alerts.domain.model.commands.ResolveIncidentCommand;
import com.test.backend.alerts.domain.model.entities.CorrectiveAction;
import com.test.backend.alerts.domain.model.entities.EventOutbox;
import com.test.backend.alerts.domain.services.DuplicateIncidentPolicy;
import com.test.backend.alerts.domain.services.IncidentCommandService;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.EventOutboxRepository;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import com.test.backend.alerts.infrastructure.security.JWTSecurityContextAdapter;
import com.test.backend.compliance.domain.model.aggregates.AuditTrail;
import com.test.backend.compliance.infrastructure.persistence.jpa.repositories.AuditTrailRepository;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class IncidentCommandServiceImpl implements IncidentCommandService {

    private final IncidentRepository incidentRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final EventOutboxRepository eventOutboxRepository;
    private final AuditTrailRepository auditTrailRepository;
    private final DuplicateIncidentPolicy duplicateIncidentPolicy;
    private final JWTSecurityContextAdapter jwtSecurityContextAdapter;

    public IncidentCommandServiceImpl(
            IncidentRepository incidentRepository,
            LaboratoryRepository laboratoryRepository,
            EventOutboxRepository eventOutboxRepository,
            AuditTrailRepository auditTrailRepository,
            DuplicateIncidentPolicy duplicateIncidentPolicy,
            JWTSecurityContextAdapter jwtSecurityContextAdapter
    ) {
        this.incidentRepository = incidentRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.eventOutboxRepository = eventOutboxRepository;
        this.auditTrailRepository = auditTrailRepository;
        this.duplicateIncidentPolicy = duplicateIncidentPolicy;
        this.jwtSecurityContextAdapter = jwtSecurityContextAdapter;
    }

    @Override
    public Optional<Incident> handle(CreateIncidentCommand command) {
        if (duplicateIncidentPolicy.hasActiveIncident(command.laboratoryId())) {
            throw new IllegalStateException("An active incident already exists for laboratory ID " + command.laboratoryId());
        }

        var workspaceIdOpt = jwtSecurityContextAdapter.getCurrentWorkspaceId();
        var laboratoryOpt = (workspaceIdOpt.isPresent() && !jwtSecurityContextAdapter.isCurrentUserAdmin())
                ? laboratoryRepository.findByIdAndWorkspaceId(command.laboratoryId(), workspaceIdOpt.get())
                : laboratoryRepository.findById(command.laboratoryId());

        if (laboratoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Laboratory not found or access denied for ID " + command.laboratoryId());
        }

        var laboratory = laboratoryOpt.get();
        var incident = new Incident(
                laboratory,
                command.title(),
                command.description(),
                command.severity(),
                command.escalationMaxMins(),
                command.escalationNextRole()
        );

        var savedIncident = incidentRepository.save(incident);

        var outbox = new EventOutbox(
                "INCIDENT_CREATED",
                "Incident",
                String.valueOf(savedIncident.getId()),
                "{\"incidentId\":" + savedIncident.getId() + ",\"laboratoryId\":" + laboratory.getId() + ",\"severity\":\"" + savedIncident.getSeverity() + "\"}",
                laboratory.getWorkspace() != null ? String.valueOf(laboratory.getWorkspace().getId()) : null
        );
        eventOutboxRepository.save(outbox);

        var actor = jwtSecurityContextAdapter.getCurrentActor();
        var signature = calculateHash(savedIncident.getId(), "CREATE_INCIDENT", actor, null, "OPEN");
        var audit = new AuditTrail(
                String.valueOf(savedIncident.getId()),
                "CREATE_INCIDENT",
                actor,
                null,
                "OPEN",
                "Incident opened for laboratory " + laboratory.getName() + " with severity " + savedIncident.getSeverity(),
                signature
        );
        auditTrailRepository.save(audit);

        return Optional.of(savedIncident);
    }

    @Override
    public Optional<Incident> handle(AcknowledgeIncidentCommand command) {
        var incidentOpt = findAccessibleIncident(command.incidentId());
        if (incidentOpt.isEmpty()) {
            return Optional.empty();
        }

        var incident = incidentOpt.get();
        String previousState = incident.getStatus();
        incident.acknowledge();

        var savedIncident = incidentRepository.save(incident);

        var outbox = new EventOutbox(
                "INCIDENT_ACKNOWLEDGED",
                "Incident",
                String.valueOf(savedIncident.getId()),
                "{\"incidentId\":" + savedIncident.getId() + ",\"status\":\"ACKNOWLEDGED\"}",
                savedIncident.getLaboratory().getWorkspace() != null ? String.valueOf(savedIncident.getLaboratory().getWorkspace().getId()) : null
        );
        eventOutboxRepository.save(outbox);

        var actor = jwtSecurityContextAdapter.getCurrentActor();
        var signature = calculateHash(savedIncident.getId(), "ACKNOWLEDGE_INCIDENT", actor, previousState, "ACKNOWLEDGED");
        var audit = new AuditTrail(
                String.valueOf(savedIncident.getId()),
                "ACKNOWLEDGE_INCIDENT",
                actor,
                previousState,
                "ACKNOWLEDGED",
                "Incident acknowledged by " + actor,
                signature
        );
        auditTrailRepository.save(audit);

        return Optional.of(savedIncident);
    }

    @Override
    public Optional<Incident> handle(ResolveIncidentCommand command) {
        var incidentOpt = findAccessibleIncident(command.incidentId());
        if (incidentOpt.isEmpty()) {
            return Optional.empty();
        }

        var incident = incidentOpt.get();
        String previousState = incident.getStatus();
        var correctiveAction = new CorrectiveAction(incident, command.technicianId(), command.actionTaken());
        incident.resolve(correctiveAction);

        var savedIncident = incidentRepository.save(incident);

        var outbox = new EventOutbox(
                "INCIDENT_RESOLVED",
                "Incident",
                String.valueOf(savedIncident.getId()),
                "{\"incidentId\":" + savedIncident.getId() + ",\"technicianId\":" + command.technicianId() + "}",
                savedIncident.getLaboratory().getWorkspace() != null ? String.valueOf(savedIncident.getLaboratory().getWorkspace().getId()) : null
        );
        eventOutboxRepository.save(outbox);

        var actor = jwtSecurityContextAdapter.getCurrentActor();
        var signature = calculateHash(savedIncident.getId(), "RESOLVE_INCIDENT", actor, previousState, "RESOLVED");
        var audit = new AuditTrail(
                String.valueOf(savedIncident.getId()),
                "RESOLVE_INCIDENT",
                actor,
                previousState,
                "RESOLVED",
                "Resolution action: " + command.actionTaken(),
                signature
        );
        auditTrailRepository.save(audit);

        return Optional.of(savedIncident);
    }

    @Override
    public Optional<Incident> handle(ApplyEscalationCommand command) {
        var incidentOpt = findAccessibleIncident(command.incidentId());
        if (incidentOpt.isEmpty()) {
            return Optional.empty();
        }

        var incident = incidentOpt.get();
        String previousState = incident.getStatus();
        incident.escalate(command.nextRole());

        var savedIncident = incidentRepository.save(incident);

        var outbox = new EventOutbox(
                "INCIDENT_ESCALATED",
                "Incident",
                String.valueOf(savedIncident.getId()),
                "{\"incidentId\":" + savedIncident.getId() + ",\"escalatedTo\":\"" + savedIncident.getEscalationNextRole() + "\"}",
                savedIncident.getLaboratory().getWorkspace() != null ? String.valueOf(savedIncident.getLaboratory().getWorkspace().getId()) : null
        );
        eventOutboxRepository.save(outbox);

        var actor = jwtSecurityContextAdapter.getCurrentActor();
        var signature = calculateHash(savedIncident.getId(), "ESCALATE_INCIDENT", actor, previousState, "ESCALATED");
        var audit = new AuditTrail(
                String.valueOf(savedIncident.getId()),
                "ESCALATE_INCIDENT",
                actor,
                previousState,
                "ESCALATED",
                "Incident escalated to " + savedIncident.getEscalationNextRole(),
                signature
        );
        auditTrailRepository.save(audit);

        return Optional.of(savedIncident);
    }

    private Optional<Incident> findAccessibleIncident(Long incidentId) {
        var workspaceIdOpt = jwtSecurityContextAdapter.getCurrentWorkspaceId();
        if (workspaceIdOpt.isPresent() && !jwtSecurityContextAdapter.isCurrentUserAdmin()) {
            return incidentRepository.findByIdAndLaboratoryWorkspaceId(incidentId, workspaceIdOpt.get());
        }
        return incidentRepository.findById(incidentId);
    }

    private String calculateHash(Long entityId, String action, String actor, String previousState, String newState) {
        try {
            var raw = entityId + "|" + action + "|" + actor + "|" + previousState + "|" + newState + "|" + System.currentTimeMillis();
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encoded);
        } catch (Exception e) {
            return UUID.randomUUID().toString();
        }
    }
}
