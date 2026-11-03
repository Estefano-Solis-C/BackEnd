package com.test.backend.alerts.application.internal.queryservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.queries.GetIncidentByIdQuery;
import com.test.backend.alerts.domain.model.queries.GetIncidentsQuery;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import com.test.backend.alerts.infrastructure.security.JWTSecurityContextAdapter;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentQueryServiceImplTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private JWTSecurityContextAdapter jwtSecurityContextAdapter;

    @InjectMocks
    private IncidentQueryServiceImpl service;

    @Test
    @DisplayName("Retrieves incidents filtered strictly by current workspace")
    void shouldRetrieveIncidentsScopedByWorkspace() {
        // Arrange
        var lab = new Laboratory();
        var incident = new Incident(lab, "Leak", "Sensor 01 leak", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 1L);

        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(5L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByLaboratoryWorkspaceId(5L)).thenReturn(List.of(incident));

        // Act
        var result = service.handle(new GetIncidentsQuery(null, null));

        // Assert
        assertEquals(1, result.size());
        assertEquals("Leak", result.getFirst().getTitle());
    }

    @Test
    @DisplayName("Retrieves single incident scoped by workspace")
    void shouldRetrieveSingleIncidentScopedByWorkspace() {
        // Arrange
        var lab = new Laboratory();
        var incident = new Incident(lab, "Overheat", "Overheat alert", "CRITICAL", 15, "COORDINATOR");
        ReflectionTestUtils.setField(incident, "id", 50L);

        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(5L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(50L, 5L)).thenReturn(Optional.of(incident));

        // Act
        var result = service.handle(new GetIncidentByIdQuery(50L));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Overheat", result.get().getTitle());
    }

    @Test
    @DisplayName("Returns empty optional when incident does not exist in the tenant workspace")
    void shouldReturnEmptyWhenIncidentDoesNotExistInWorkspace() {
        // Arrange
        Long incidentId = 999L;
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(5L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByIdAndLaboratoryWorkspaceId(incidentId, 5L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(new GetIncidentByIdQuery(incidentId));

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Returns empty list when no incidents exist in the workspace")
    void shouldReturnEmptyListWhenNoIncidentsExistInWorkspace() {
        // Arrange
        when(jwtSecurityContextAdapter.getCurrentWorkspaceId()).thenReturn(Optional.of(5L));
        when(jwtSecurityContextAdapter.isCurrentUserAdmin()).thenReturn(false);
        when(incidentRepository.findByLaboratoryWorkspaceId(5L)).thenReturn(List.of());

        // Act
        var result = service.handle(new GetIncidentsQuery(null, null));

        // Assert
        assertTrue(result.isEmpty());
    }
}
