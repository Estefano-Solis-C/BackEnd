package com.test.backend.alerts.domain.services;

import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuplicateIncidentPolicyTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private DuplicateIncidentPolicy policy;

    @Test
    @DisplayName("Returns false when no active incident exists for the laboratory")
    void shouldReturnFalseWhenNoActiveIncidentExists() {
        // Arrange
        Long labId = 1L;
        when(incidentRepository.existsByLaboratoryIdAndStatusIn(eq(labId), any())).thenReturn(false);

        // Act
        boolean result = policy.hasActiveIncident(labId);

        // Assert
        assertFalse(result);
        verify(incidentRepository).existsByLaboratoryIdAndStatusIn(labId, List.of("OPEN", "ACKNOWLEDGED", "ESCALATED"));
    }

    @Test
    @DisplayName("Returns true when an active incident is currently open or escalated")
    void shouldReturnTrueWhenActiveIncidentExists() {
        // Arrange
        Long labId = 2L;
        when(incidentRepository.existsByLaboratoryIdAndStatusIn(eq(labId), any())).thenReturn(true);

        // Act
        boolean result = policy.hasActiveIncident(labId);

        // Assert
        assertTrue(result);
        verify(incidentRepository).existsByLaboratoryIdAndStatusIn(labId, List.of("OPEN", "ACKNOWLEDGED", "ESCALATED"));
    }

    @Test
    @DisplayName("Returns false safely when laboratory ID is null")
    void shouldReturnFalseSafelyWhenLaboratoryIdIsNull() {
        // Arrange
        Long nullLabId = null;

        // Act
        boolean result = policy.hasActiveIncident(nullLabId);

        // Assert
        assertFalse(result);
        verify(incidentRepository, never()).existsByLaboratoryIdAndStatusIn(any(), any());
    }
}
