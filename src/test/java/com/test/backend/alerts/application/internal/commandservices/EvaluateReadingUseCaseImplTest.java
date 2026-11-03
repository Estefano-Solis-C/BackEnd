package com.test.backend.alerts.application.internal.commandservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.commands.CreateIncidentCommand;
import com.test.backend.alerts.domain.services.DuplicateIncidentPolicy;
import com.test.backend.alerts.domain.services.IncidentCommandService;
import com.test.backend.alerts.domain.services.ThermalExcursionEvaluator;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluateReadingUseCaseImplTest {

    @Mock
    private ThermalExcursionEvaluator thermalExcursionEvaluator;

    @Mock
    private DuplicateIncidentPolicy duplicateIncidentPolicy;

    @Mock
    private IncidentCommandService incidentCommandService;

    @InjectMocks
    private EvaluateReadingUseCaseImpl useCase;

    @Test
    @DisplayName("Creates and returns incident when thermal breach occurs and no active incident exists")
    void shouldCreateIncidentWhenThermalBreachOccursWithoutActiveIncident() {
        // Arrange
        Long labId = 1L;
        double temperature = 14.0;
        when(thermalExcursionEvaluator.evaluate(temperature, 2.0, 8.0))
                .thenReturn(ThermalExcursionEvaluator.ExcursionSeverity.CRITICAL);
        when(duplicateIncidentPolicy.hasActiveIncident(labId)).thenReturn(false);

        var incident = new Incident(new Laboratory(), "Title", "Desc", "CRITICAL", 15, "COORDINATOR");
        when(incidentCommandService.handle(any(CreateIncidentCommand.class))).thenReturn(Optional.of(incident));

        // Act
        var result = useCase.evaluate(labId, temperature, 2.0, 8.0, "Microbiology Lab");

        // Assert
        assertTrue(result.isPresent());
        verify(incidentCommandService).handle(argThat((CreateIncidentCommand cmd) ->
                cmd.laboratoryId().equals(labId) && "CRITICAL".equals(cmd.severity())
        ));
    }

    @Test
    @DisplayName("Skips incident creation when an active incident already exists for that laboratory")
    void shouldSkipIncidentCreationWhenActiveIncidentAlreadyExists() {
        // Arrange
        Long labId = 1L;
        double temperature = 14.0;
        when(thermalExcursionEvaluator.evaluate(temperature, 2.0, 8.0))
                .thenReturn(ThermalExcursionEvaluator.ExcursionSeverity.CRITICAL);
        when(duplicateIncidentPolicy.hasActiveIncident(labId)).thenReturn(true);

        // Act
        var result = useCase.evaluate(labId, temperature, 2.0, 8.0, "Microbiology Lab");

        // Assert
        assertFalse(result.isPresent());
        verify(incidentCommandService, never()).handle(any(CreateIncidentCommand.class));
    }

    @Test
    @DisplayName("Returns empty optional without querying policy when temperature reading is normal")
    void shouldReturnEmptySafelyWhenTemperatureIsNormal() {
        // Arrange
        Long labId = 1L;
        double normalTemp = 4.0;
        when(thermalExcursionEvaluator.evaluate(normalTemp, 2.0, 8.0))
                .thenReturn(ThermalExcursionEvaluator.ExcursionSeverity.NORMAL);

        // Act
        var result = useCase.evaluate(labId, normalTemp, 2.0, 8.0, "Microbiology Lab");

        // Assert
        assertFalse(result.isPresent());
        verify(duplicateIncidentPolicy, never()).hasActiveIncident(any());
        verify(incidentCommandService, never()).handle(any(CreateIncidentCommand.class));
    }
}
