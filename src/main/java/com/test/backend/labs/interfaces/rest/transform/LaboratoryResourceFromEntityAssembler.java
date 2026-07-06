package com.test.backend.labs.interfaces.rest.transform;

import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.valueobjets.NotificationPreferences;
import com.test.backend.labs.interfaces.rest.resources.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LaboratoryResourceFromEntityAssembler {
    public static LaboratoryResource toResourceFromEntity(Laboratory entity) {
        if (entity == null) return null;

        // Map metrics
        List<LabMetricResource> metrics = entity.getMetrics() == null ? new ArrayList<>() :
            entity.getMetrics().stream()
                .map(m -> new LabMetricResource(
                    m.getName(),
                    m.getValue(),
                    m.getUnit(),
                    m.getStatus(),
                    m.getIcon(),
                    parseSparkline(m.getSparkline()),
                    m.getThreshold(),
                    m.getObjectType()
                ))
                .collect(Collectors.toList());

        // Map alerts
        List<LabAlertResource> alerts = entity.getAlerts() == null ? new ArrayList<>() :
            entity.getAlerts().stream()
                .map(a -> new LabAlertResource(
                    a.getAlertId() != null ? a.getAlertId() : a.getId(),
                    a.getTitle(),
                    a.getSource(),
                    a.getTimeAgo(),
                    a.getSeverity()
                ))
                .collect(Collectors.toList());

        // Map activities
        List<LabActivityResource> activities = entity.getActivities() == null ? new ArrayList<>() :
            entity.getActivities().stream()
                .map(act -> new LabActivityResource(
                    act.getId(),
                    act.getTitle(),
                    act.getDescription(),
                    act.getTimestamp(),
                    act.getIcon()
                ))
                .collect(Collectors.toList());

        // Map schedules
        List<LabScheduleResource> schedules = entity.getSchedules() == null ? new ArrayList<>() :
            entity.getSchedules().stream()
                .map(s -> new LabScheduleResource(
                    s.getId(),
                    s.getName(),
                    s.getTimeRange(),
                    s.isActive(),
                    s.getIcon()
                ))
                .collect(Collectors.toList());

        // Map MetricSubscriptions
        List<MetricSubscriptionResource> metricSubscriptions = entity.getMetricSubscriptions() == null ? new ArrayList<>() :
            entity.getMetricSubscriptions().stream()
                .map(sub -> new MetricSubscriptionResource(
                    sub.getMetricType().getId(),
                    sub.getMetricType().getKey(),
                    sub.getMetricType().getDisplayName(),
                    sub.getMetricType().getUnit(),
                    sub.getMetricType().getIcon(),
                    sub.getMetricType().getCategory(),
                    sub.getMinThreshold(),
                    sub.getMaxThreshold(),
                    sub.isActive()
                ))
                .collect(Collectors.toList());

        // Map NotificationPreferences
        NotificationPreferencesResource notifications = null;
        NotificationPreferences np = entity.getNotificationPreferences();
        if (np != null) {
            notifications = new NotificationPreferencesResource(
                np.isEmail(),
                np.isSms(),
                np.isPush(),
                np.isCriticalOnly()
            );
        }

        return new LaboratoryResource(
            entity.getId(),
            entity.getName(),
            entity.getType(),
            entity.getStatus(),
            entity.getBuilding(),
            entity.getFloor(),
            entity.getLabCode(),
            entity.getOverallStatus(),
            entity.isActive(),
            entity.getLastUpdate() != null ? entity.getLastUpdate().atZone(java.time.ZoneOffset.UTC).toInstant() : null,
            entity.isLive(),
            entity.getNextMaintenance(),
            entity.getMaintenanceDaysLeft(),
            entity.getRoomNumber(),
            entity.getDescription(),
            metrics,
            alerts,
            activities,
            schedules,
            metricSubscriptions,
            notifications
        );
    }

    private static List<Double> parseSparkline(String sparklineStr) {
        if (sparklineStr == null || sparklineStr.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return Arrays.stream(sparklineStr.split(","))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }
}
