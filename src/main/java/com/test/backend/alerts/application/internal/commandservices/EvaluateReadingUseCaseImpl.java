package com.test.backend.alerts.application.internal.commandservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.commands.CreateIncidentCommand;
import com.test.backend.alerts.domain.services.DuplicateIncidentPolicy;
import com.test.backend.alerts.domain.services.EvaluateReadingUseCase;
import com.test.backend.alerts.domain.services.IncidentCommandService;
import com.test.backend.alerts.domain.services.ThermalExcursionEvaluator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EvaluateReadingUseCaseImpl implements EvaluateReadingUseCase {

    private final ThermalExcursionEvaluator thermalExcursionEvaluator;
    private final DuplicateIncidentPolicy duplicateIncidentPolicy;
    private final IncidentCommandService incidentCommandService;

    public EvaluateReadingUseCaseImpl(
            ThermalExcursionEvaluator thermalExcursionEvaluator,
            DuplicateIncidentPolicy duplicateIncidentPolicy,
            IncidentCommandService incidentCommandService
    ) {
        this.thermalExcursionEvaluator = thermalExcursionEvaluator;
        this.duplicateIncidentPolicy = duplicateIncidentPolicy;
        this.incidentCommandService = incidentCommandService;
    }

    @Override
    public Optional<Incident> evaluate(Long laboratoryId, Double temperature, Double minThreshold, Double maxThreshold, String labName) {
        var excursion = thermalExcursionEvaluator.evaluate(temperature, minThreshold, maxThreshold);
        if (excursion == ThermalExcursionEvaluator.ExcursionSeverity.NORMAL) {
            return Optional.empty();
        }

        if (duplicateIncidentPolicy.hasActiveIncident(laboratoryId)) {
            return Optional.empty();
        }

        String title = "Thermal Excursion detected in " + (labName != null ? labName : ("Lab #" + laboratoryId));
        String description = "Recorded temperature of " + temperature + "°C breached acceptable operating limits ("
                + (minThreshold != null ? minThreshold : ThermalExcursionEvaluator.DEFAULT_MIN_TEMP) + "°C to "
                + (maxThreshold != null ? maxThreshold : ThermalExcursionEvaluator.DEFAULT_MAX_TEMP) + "°C).";

        var command = new CreateIncidentCommand(
                laboratoryId,
                title,
                description,
                excursion.name(),
                15,
                "SAFETY_COORDINATOR"
        );

        return incidentCommandService.handle(command);
    }
}
