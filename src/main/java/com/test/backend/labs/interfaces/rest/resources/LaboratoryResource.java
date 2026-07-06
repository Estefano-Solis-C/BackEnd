package com.test.backend.labs.interfaces.rest.resources;

import java.util.List;

public record LaboratoryResource(
    Long id,
    String name,
    String type,
    String status,
    String building,
    String floor,
    String labCode,
    String overallStatus,
    boolean active,
    java.time.Instant lastUpdate,
    boolean isLive,
    java.time.LocalDate nextMaintenance,
    Integer maintenanceDaysLeft,
    String roomNumber,
    String description,
    List<LabMetricResource> metrics,
    List<LabAlertResource> recentAlerts,
    List<LabActivityResource> recentActivities,
    List<LabScheduleResource> schedules,
    List<MetricSubscriptionResource> metricSubscriptions,
    NotificationPreferencesResource notifications
) {}
