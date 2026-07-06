package com.test.backend.alerts.interfaces.rest.transform;

import com.test.backend.alerts.domain.model.aggregates.Alert;
import com.test.backend.alerts.interfaces.rest.resources.AlertMetricResource;
import com.test.backend.alerts.interfaces.rest.resources.AlertResource;

import java.util.ArrayList;
import java.util.List;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert entity) {
        if (entity == null) return null;

        String createdAtStr = entity.getCreatedAt() != null 
                ? entity.getCreatedAt().toInstant().toString() 
                : null;

        Long laboratoryId = entity.getLaboratory() != null 
                ? entity.getLaboratory().getId() 
                : null;

        String labName = entity.getLaboratory() != null 
                ? entity.getLaboratory().getName() 
                : entity.getLabName();

        String labLocation = "N/A";
        if (entity.getLaboratory() != null) {
            String building = entity.getLaboratory().getBuilding() != null ? entity.getLaboratory().getBuilding() : "";
            String floor = entity.getLaboratory().getFloor() != null ? entity.getLaboratory().getFloor() : "";
            String roomNumber = entity.getLaboratory().getRoomNumber() != null ? entity.getLaboratory().getRoomNumber() : "";
            labLocation = building + " - Floor " + floor + " - Room " + roomNumber;
        }

        Long sensorId = entity.getSensorConfiguration() != null 
                ? entity.getSensorConfiguration().getId() 
                : null;

        String sensorName = entity.getSensorConfiguration() != null 
                ? entity.getSensorConfiguration().getSensorName() 
                : "N/A";

        String equipmentName = (entity.getSensorConfiguration() != null && entity.getSensorConfiguration().getEquipment() != null)
                ? entity.getSensorConfiguration().getEquipment().getName()
                : null;

        List<AlertMetricResource> metrics = new ArrayList<>();
        if (entity.getMetrics() != null) {
            for (var m : entity.getMetrics()) {
                metrics.add(new AlertMetricResource(m.getLabel(), m.getValue()));
            }
        }

        // Dynamically add minThreshold and maxThreshold if they are not already present
        boolean hasMin = metrics.stream().anyMatch(m -> "minThreshold".equals(m.label()));
        boolean hasMax = metrics.stream().anyMatch(m -> "maxThreshold".equals(m.label()));

        Double minThreshold = null;
        Double maxThreshold = null;
        if (entity.getSensorConfiguration() != null) {
            minThreshold = entity.getSensorConfiguration().getMinThreshold();
            maxThreshold = entity.getSensorConfiguration().getMaxThreshold();
            if (entity.getSensorConfiguration().getEquipment() != null) {
                if (entity.getSensorConfiguration().getEquipment().getMinThreshold() != null) {
                    minThreshold = entity.getSensorConfiguration().getEquipment().getMinThreshold();
                }
                if (entity.getSensorConfiguration().getEquipment().getMaxThreshold() != null) {
                    maxThreshold = entity.getSensorConfiguration().getEquipment().getMaxThreshold();
                }
            }
        }

        String unit = (entity.getSensorConfiguration() != null) ? entity.getSensorConfiguration().getUnit() : "";
        if (unit == null) unit = "";

        if (!hasMin && minThreshold != null) {
            metrics.add(new AlertMetricResource("minThreshold", String.format(java.util.Locale.US, "%.2f", minThreshold) + unit));
        }
        if (!hasMax && maxThreshold != null) {
            metrics.add(new AlertMetricResource("maxThreshold", String.format(java.util.Locale.US, "%.2f", maxThreshold) + unit));
        }

        return new AlertResource(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getSeverity(),
            entity.getStatus(),
            createdAtStr,
            laboratoryId,
            labName,
            labLocation,
            sensorId,
            sensorName,
            equipmentName,
            metrics
        );
    }
}
