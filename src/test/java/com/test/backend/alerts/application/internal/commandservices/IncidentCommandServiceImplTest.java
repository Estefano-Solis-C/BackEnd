package com.test.backend.alerts.application.internal.commandservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.commands.AcknowledgeIncidentCommand;
import com.test.backend.alerts.domain.model.commands.ApplyEscalationCommand;
import com.test.backend.alerts.domain.model.commands.CreateIncidentCommand;
import com.test.backend.alerts.domain.model.commands.ResolveIncidentCommand;
import com.test.backend.alerts.domain.model.entities.EventOutbox;
import com.test.backend.alerts.domain.services.DuplicateIncidentPolicy;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.EventOutboxRepository;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import com.test.backend.alerts.infrastructure.security.JWTSecurityContextAdapter;
import com.test.backend.compliance.domain.model.aggregates.AuditTrail;
import com.test.backend.compliance.infrastructure.persistence.jpa.repositories.AuditTrailRepository;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.aggregates.Workspace;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentCommandServiceImplTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @Mock
    private EventOutboxRepository eventOutboxRepository;

    @Mock
    private AuditTrailRepository auditTrailRepository;

    @Mock
    private DuplicateIncidentPolicy duplicateIncidentPolicy;

    @Mock
    private JWTSecurityContextAdapter jwtSecurityContextAdapter;

    @InjectMocks
    private IncidentCommandServiceImpl service;

    private Laboratory sampleLab;
    private Workspace sampleWorkspace;

    @BeforeEach
    void setUp() {
        sampleWorkspace = new Workspace();
        ReflectionTestUtils.setField(sampleWorkspace, "id", 10L);

        sampleLab = new Laboratory();
        ReflectionTestUtils.setField(sampleLab, "id", 1L);
        sampleLab.setName("Molecular Biology Lab");
        sampleLab.setWorkspace(sampleWorkspace);
    }

    @Test
    @DisplayName("Persists incident aggregate, event outbox and signed audit trail when command is valid")
    void shouldPersistIncidentOutboxAndAuditWhenCommandIsValid() {
        // Arrange
        var command = new CreateIncidentCommand(1L, "Critical Freeze", "Temperature dropped to -10C", "CRITICAL", 15, "COORDINATOR");
        when(duplicateIncidentPolicy.hasActiveIncident(1L)).thenReturn(false);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(laboratoryRepository.findByIdAndWorkspaceId(1L, 10L)).thenReturn(Optional.of(sampleLab));
        when(jwtSecurityContextAdapter.getCurrentActor()).thenReturn("analyst@safelab.com");
        when(incidentRepository.save(any(Incident.class))).thenAnswer(i -> {
            Incident inc = i.getArgument(0);
            ReflectionTestUtils.setField(inc, "id", 100L);
            return inc;
        });

        // Act
        var result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("OPEN", result.get().getStatus());
        assertEquals("CRITICAL", result.get().getSeverity());
        verify(incidentRepository).save(any(Incident.class));
        verify(eventOutboxRepository).save(argThat(outbox -> "INCIDENT_CREATED".equals(outbox.getEventType())));
        verify(auditTrailRepository).save(argThat(audit -> "CREATE_INCIDENT".equals(audit.getAction()) && audit.getSignature() != null));
    }

    @Test
    @DisplayName("Transitions status to ACKNOWLEDGED and emits outbox and audit events")
    void shouldTransitionToAcknowledgedAndEmitEvents() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 200L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(200L, 10L)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        when(jwtSecurityContextAdapter.getCurrentActor()).thenReturn("tech@safelab.com");

        // Act
        var result = service.handle(new AcknowledgeIncidentCommand(200L));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ACKNOWLEDGED", result.get().getStatus());
        verify(eventOutboxRepository).save(argThat(outbox -> "INCIDENT_ACKNOWLEDGED".equals(outbox.getEventType())));
        verify(auditTrailRepository).save(argThat(audit -> "ACKNOWLEDGE_INCIDENT".equals(audit.getAction())));
    }

    @Test
    @DisplayName("Attaches corrective action and transitions status to RESOLVED with SHA-256 signature")
    void shouldAttachCorrectiveActionAndTransitionToResolved() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 300L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(300L, 10L)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        when(jwtSecurityContextAdapter.getCurrentActor()).thenReturn("tech@safelab.com");

        // Act
        var result = service.handle(new ResolveIncidentCommand(300L, "Replaced faulty thermocouple sensor in rack A", 55L));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("RESOLVED", result.get().getStatus());
        assertNotNull(result.get().getCorrectiveAction());
        assertEquals("Replaced faulty thermocouple sensor in rack A", result.get().getCorrectiveAction().getActionTaken());
        assertNotNull(result.get().getResolvedAt());
        verify(eventOutboxRepository).save(argThat(outbox -> "INCIDENT_RESOLVED".equals(outbox.getEventType())));
        verify(auditTrailRepository).save(argThat(audit -> "RESOLVE_INCIDENT".equals(audit.getAction()) && audit.getSignature() != null));
    }

    @Test
    @DisplayName("Updates status to ESCALATED and records next role")
    void shouldUpdateStatusToEscalatedAndRecordNextRole() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 400L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(400L, 10L)).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        when(jwtSecurityContextAdapter.getCurrentActor()).thenReturn("system-escalation");

        // Act
        var result = service.handle(new ApplyEscalationCommand(400L, "DIRECTOR_OF_QUALITY"));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ESCALATED", result.get().getStatus());
        assertEquals("DIRECTOR_OF_QUALITY", result.get().getEscalationNextRole());
        verify(eventOutboxRepository).save(argThat(outbox -> "INCIDENT_ESCALATED".equals(outbox.getEventType())));
        verify(auditTrailRepository).save(argThat(audit -> "ESCALATE_INCIDENT".equals(audit.getAction())));
    }

    @Test
    @DisplayName("Throws IllegalStateException when active incident already exists for the laboratory")
    void shouldThrowIllegalStateExceptionWhenDuplicateActiveIncidentExists() {
        // Arrange
        var command = new CreateIncidentCommand(1L, "Excursion", "Temp spike", "CRITICAL", 15, "COORDINATOR");
        when(duplicateIncidentPolicy.hasActiveIncident(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> service.handle(command));
        verify(incidentRepository, never()).save(any());
        verify(eventOutboxRepository, never()).save(any());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when laboratory does not exist or workspace access is denied")
    void shouldThrowIllegalArgumentExceptionWhenLabNotFoundOrAccessDenied() {
        // Arrange
        var command = new CreateIncidentCommand(999L, "Title", "Desc", "CRITICAL", 15, "COORDINATOR");
        when(duplicateIncidentPolicy.hasActiveIncident(999L)).thenReturn(false);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(laboratoryRepository.findByIdAndWorkspaceId(999L, 10L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(incidentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when corrective action text is shorter than 10 characters")
    void shouldThrowIllegalArgumentExceptionWhenActionTakenIsTooShort() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 300L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(300L, 10L)).thenReturn(Optional.of(incident));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                service.handle(new ResolveIncidentCommand(300L, "Fixed it", 55L))
        );
        verify(incidentRepository, never()).save(any());
        verify(eventOutboxRepository, never()).save(any());
    }

    @Test
    @DisplayName("Throws IllegalStateException when attempting to acknowledge an already resolved incident")
    void shouldThrowIllegalStateExceptionWhenAcknowledgingResolvedIncident() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        incident.setStatus("RESOLVED");
        ReflectionTestUtils.setField(incident, "id", 200L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(200L, 10L)).thenReturn(Optional.of(incident));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> service.handle(new AcknowledgeIncidentCommand(200L)));
        verify(incidentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Throws IllegalStateException when attempting to escalate an already resolved incident")
    void shouldThrowIllegalStateExceptionWhenEscalatingResolvedIncident() {
        // Arrange
        var incident = new Incident(sampleLab, "Spike", "Detail", "CRITICAL", 15, "COORDINATOR");
        incident.setStatus("RESOLVED");
        ReflectionTestUtils.setField(incident, "id", 400L);
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(400L, 10L)).thenReturn(Optional.of(incident));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> service.handle(new ApplyEscalationCommand(400L, "DIRECTOR")));
        verify(incidentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Returns empty optional when accessing an incident from another workspace")
    void shouldReturnEmptyWhenAccessingCrossWorkspaceIncident() {
        // Arrange
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(999L, 10L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(new AcknowledgeIncidentCommand(999L));

        // Assert
        assertTrue(result.isEmpty());
        verify(incidentRepository, never()).save(any());
        verify(eventOutboxRepository, never()).save(any());
    }

    @Test
    @DisplayName("Returns empty optional when resolving a non-existent incident")
    void shouldReturnEmptyWhenResolvingNonExistentIncident() {
        // Arrange
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(888L, 10L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(new ResolveIncidentCommand(888L, "Replaced thermal probe with certified spare", 1L));

        // Assert
        assertTrue(result.isEmpty());
        verify(incidentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Returns empty optional when escalating a non-existent incident")
    void shouldReturnEmptyWhenEscalatingNonExistentIncident() {
        // Arrange
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(10L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(777L, 10L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(new ApplyEscalationCommand(777L, "SAFETY_OFFICER"));

        // Assert
        assertTrue(result.isEmpty());
        verify(incidentRepository, never()).save(any());
    }
}
