package com.test.backend.labs.application.internal.commandservices;

import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.commands.CreateLaboratoryCommand;
import com.test.backend.labs.domain.model.commands.UpdateLaboratoryCommand;
import com.test.backend.labs.domain.model.commands.DeleteLaboratoryCommand;
import com.test.backend.labs.domain.model.entities.LabMetricSubscription;
import com.test.backend.labs.domain.services.LaboratoryCommandService;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.MetricTypeRepository;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.WorkspaceRepository;
import com.test.backend.shared.application.CurrentWorkspaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import jakarta.persistence.EntityManager;

@Service
public class LaboratoryCommandServiceImpl implements LaboratoryCommandService {

    private final LaboratoryRepository laboratoryRepository;
    private final MetricTypeRepository metricTypeRepository;
    private final WorkspaceRepository workspaceRepository;
    private final CurrentWorkspaceService currentWorkspaceService;
    private final EntityManager entityManager;

    public LaboratoryCommandServiceImpl(LaboratoryRepository laboratoryRepository,
                                         MetricTypeRepository metricTypeRepository,
                                         WorkspaceRepository workspaceRepository,
                                         CurrentWorkspaceService currentWorkspaceService,
                                         EntityManager entityManager) {
        this.laboratoryRepository = laboratoryRepository;
        this.metricTypeRepository = metricTypeRepository;
        this.workspaceRepository = workspaceRepository;
        this.currentWorkspaceService = currentWorkspaceService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Optional<Laboratory> handle(CreateLaboratoryCommand command) {
        if (laboratoryRepository.findByLabCode(command.labCode()).isPresent()) {
            throw new IllegalArgumentException("Laboratory with code " + command.labCode() + " already exists");
        }

        Long workspaceId = currentWorkspaceService.getCurrentWorkspaceId()
                .orElseThrow(() -> new IllegalStateException("User does not have an active workspace"));
        var workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalStateException("Workspace not found for ID: " + workspaceId));

        var laboratory = new Laboratory(command);
        laboratory.setWorkspace(workspace);

        // Create metric subscriptions
        if (command.metricSubscriptions() != null) {
            for (var subData : command.metricSubscriptions()) {
                var metricType = metricTypeRepository.findById(subData.metricTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("MetricType with id " + subData.metricTypeId() + " not found"));

                var subscription = new LabMetricSubscription();
                subscription.setLaboratory(laboratory);
                subscription.setMetricType(metricType);
                subscription.setMinThreshold(subData.minThreshold());
                subscription.setMaxThreshold(subData.maxThreshold());
                subscription.setActive(true);
                laboratory.getMetricSubscriptions().add(subscription);
            }
        }

        laboratoryRepository.save(laboratory);
        return Optional.of(laboratory);
    }

    @Override
    @Transactional
    public Optional<Laboratory> handle(UpdateLaboratoryCommand command) {
        Long workspaceId = currentWorkspaceService.getCurrentWorkspaceId()
                .orElseThrow(() -> new IllegalStateException("User does not have an active workspace"));

        var result = laboratoryRepository.findByIdAndWorkspaceId(command.id(), workspaceId);
        if (result.isEmpty()) return Optional.empty();

        var laboratory = result.get();
        if (!laboratory.getLabCode().equals(command.labCode()) &&
            laboratoryRepository.findByLabCode(command.labCode()).isPresent()) {
            throw new IllegalArgumentException("Laboratory with code " + command.labCode() + " already exists");
        }

        laboratory.updateFrom(command);

        // Replace metric subscriptions
        if (command.metricSubscriptions() != null) {
            var newSubIds = command.metricSubscriptions().stream()
                .map(UpdateLaboratoryCommand.MetricSubscriptionData::metricTypeId)
                .toList();

            // 1. Remove subscriptions no longer present in the command
            laboratory.getMetricSubscriptions().removeIf(sub -> !newSubIds.contains(sub.getMetricType().getId()));

            // 2. Add or update subscriptions
            for (var subData : command.metricSubscriptions()) {
                var existingOpt = laboratory.getMetricSubscriptions().stream()
                    .filter(sub -> sub.getMetricType().getId().equals(subData.metricTypeId()))
                    .findFirst();

                if (existingOpt.isPresent()) {
                    var existingSub = existingOpt.get();
                    existingSub.setMinThreshold(subData.minThreshold());
                    existingSub.setMaxThreshold(subData.maxThreshold());
                    existingSub.setActive(true);
                } else {
                    var metricType = metricTypeRepository.findById(subData.metricTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("MetricType with id " + subData.metricTypeId() + " not found"));

                    var subscription = new LabMetricSubscription();
                    subscription.setLaboratory(laboratory);
                    subscription.setMetricType(metricType);
                    subscription.setMinThreshold(subData.minThreshold());
                    subscription.setMaxThreshold(subData.maxThreshold());
                    subscription.setActive(true);
                    laboratory.getMetricSubscriptions().add(subscription);
                }
            }
        }

        laboratoryRepository.save(laboratory);
        return Optional.of(laboratory);
    }

    @Override
    @Transactional
    public void handle(DeleteLaboratoryCommand command) {
        Long workspaceId = currentWorkspaceService.getCurrentWorkspaceId()
                .orElseThrow(() -> new IllegalStateException("User does not have an active workspace"));

        var laboratory = laboratoryRepository.findByIdAndWorkspaceId(command.id(), workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Laboratory not found in this workspace"));

        Long labId = laboratory.getId();

        // 1. Delete calibration records for sensors in this laboratory
        entityManager.createNativeQuery(
            "DELETE FROM calibration_records WHERE sensor_configuration_id IN (SELECT id FROM sensor_configurations WHERE laboratory_id = ?1)"
        ).setParameter(1, labId).executeUpdate();

        // 2. Delete sensor readings for this laboratory
        entityManager.createNativeQuery(
            "DELETE FROM sensor_readings WHERE laboratory_id = ?1"
        ).setParameter(1, labId).executeUpdate();

        // 3. Delete sensor configurations for this laboratory
        entityManager.createNativeQuery(
            "DELETE FROM sensor_configurations WHERE laboratory_id = ?1"
        ).setParameter(1, labId).executeUpdate();

        // 4. Delete alerts for this laboratory (and alert metrics)
        entityManager.createNativeQuery(
            "DELETE FROM alert_metrics WHERE alert_id IN (SELECT id FROM alerts WHERE laboratory_id = ?1)"
        ).setParameter(1, labId).executeUpdate();

        entityManager.createNativeQuery(
            "DELETE FROM alerts WHERE laboratory_id = ?1"
        ).setParameter(1, labId).executeUpdate();

        // 5. Delete lab user accesses for this laboratory
        entityManager.createNativeQuery(
            "DELETE FROM lab_user_accesses WHERE laboratory_id = ?1"
        ).setParameter(1, labId).executeUpdate();

        // 6. Delete laboratory
        laboratoryRepository.delete(laboratory);
    }
}
