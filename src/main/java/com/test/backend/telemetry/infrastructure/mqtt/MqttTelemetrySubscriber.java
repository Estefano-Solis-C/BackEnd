package com.test.backend.telemetry.infrastructure.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.backend.automation.domain.model.aggregates.SensorConfiguration;
import com.test.backend.automation.infrastructure.persistence.jpa.repositories.SensorConfigurationRepository;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.entities.LabMetric;
import com.test.backend.labs.domain.model.entities.LabMetricSubscription;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import com.test.backend.telemetry.domain.model.aggregates.MetricType;
import com.test.backend.telemetry.domain.model.aggregates.SensorReading;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.MetricTypeRepository;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.SensorReadingRepository;
import com.test.backend.shared.infrastructure.persistence.configuration.MqttProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.test.backend.alerts.domain.model.aggregates.Alert;
import com.test.backend.alerts.domain.model.entities.AlertMetric;
import com.test.backend.history.domain.model.aggregates.HistoryRecord;
import com.test.backend.history.infrastructure.persistence.jpa.repositories.HistoryRecordRepository;
import com.test.backend.labs.domain.model.entities.LabAlert;
import com.test.backend.automation.infrastructure.persistence.jpa.repositories.AutomationRuleRepository;
import com.test.backend.automation.domain.model.aggregates.AutomationRule;
import com.test.backend.automation.domain.model.aggregates.EquipmentThreshold;
import com.test.backend.automation.infrastructure.persistence.jpa.repositories.UserWorkspaceAccessRepository;
import com.test.backend.iam.application.internal.outboundservices.email.EmailService;
import com.test.backend.labs.domain.model.valueobjets.NotificationPreferences;

@Service
public class MqttTelemetrySubscriber implements MqttCallbackExtended {

    private static final Logger logger = LoggerFactory.getLogger(MqttTelemetrySubscriber.class);

    private final MqttClient client;
    private final MqttConnectOptions connectOptions;
    private final MqttProperties mqttProperties;
    private final SensorConfigurationRepository sensorConfigurationRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final MetricTypeRepository metricTypeRepository;
    private final AlertRepository alertRepository;
    private final AutomationRuleRepository automationRuleRepository;
    private final ObjectMapper objectMapper;
    private final TransactionTemplate transactionTemplate;
    private final HistoryRecordRepository historyRecordRepository;
    private final UserWorkspaceAccessRepository userWorkspaceAccessRepository;
    private final EmailService emailService;

    public MqttTelemetrySubscriber(MqttClient client,
                                   MqttConnectOptions connectOptions,
                                   MqttProperties mqttProperties,
                                   SensorConfigurationRepository sensorConfigurationRepository,
                                   LaboratoryRepository laboratoryRepository,
                                   SensorReadingRepository sensorReadingRepository,
                                   MetricTypeRepository metricTypeRepository,
                                   AlertRepository alertRepository,
                                   AutomationRuleRepository automationRuleRepository,
                                   PlatformTransactionManager transactionManager,
                                   HistoryRecordRepository historyRecordRepository,
                                   UserWorkspaceAccessRepository userWorkspaceAccessRepository,
                                   EmailService emailService) {
        this.client = client;
        this.connectOptions = connectOptions;
        this.mqttProperties = mqttProperties;
        this.sensorConfigurationRepository = sensorConfigurationRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.sensorReadingRepository = sensorReadingRepository;
        this.metricTypeRepository = metricTypeRepository;
        this.alertRepository = alertRepository;
        this.automationRuleRepository = automationRuleRepository;
        this.objectMapper = new ObjectMapper();
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.historyRecordRepository = historyRecordRepository;
        this.userWorkspaceAccessRepository = userWorkspaceAccessRepository;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        client.setCallback(this);
        new Thread(this::connect).start();
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
            logger.info("MQTT Client disconnected successfully.");
        } catch (MqttException e) {
            logger.error("Error closing MQTT Client", e);
        }
    }

    private void connect() {
        try {
            logger.info("Attempting to connect to MQTT broker: {}", client.getServerURI());
            client.connect(connectOptions);
            logger.info("Successfully connected to MQTT broker.");
        } catch (MqttException e) {
            logger.error("Failed to connect to MQTT broker. Single attempt finished (will not retry to avoid log spam).", e);
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        try {
            String topic = mqttProperties.getTopic() != null ? mqttProperties.getTopic() : "safelab/#";
            client.subscribe(topic, 1);
            logger.info("Subscribed to MQTT topic: {}", topic);
        } catch (MqttException e) {
            logger.error("Failed to subscribe to MQTT topic after connection", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MQTT connection lost! Reconnecting automatically...", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        logger.debug("MQTT message arrived on topic: {}, payload: {}", topic, payload);

        try {
            String[] segments = topic.split("/");
            if (segments.length < 3 || !"safelab".equalsIgnoreCase(segments[0])) {
                return;
            }

            String unit = segments[1];
            String type = segments[2];

            if ("status".equalsIgnoreCase(type)) {
                if ("OFFLINE".equalsIgnoreCase(payload)) {
                    transactionTemplate.executeWithoutResult(status -> {
                        Optional<SensorConfiguration> sensorOpt = sensorConfigurationRepository.findByUnit(unit);
                        if (sensorOpt.isPresent()) {
                            SensorConfiguration sensor = sensorOpt.get();
                            sensor.setStatus("OFFLINE");
                            sensorConfigurationRepository.save(sensor);
                            logger.info("Sensor unit '{}' is now OFFLINE (LWT).", unit);
                        }
                    });
                }
            } else if ("data".equalsIgnoreCase(type)) {
                transactionTemplate.executeWithoutResult(status -> {
                    try {
                        Optional<SensorConfiguration> sensorOpt = sensorConfigurationRepository.findByUnit(unit);
                        if (sensorOpt.isEmpty()) {
                            logger.warn("Security warning: received message for unregistered sensor unit '{}' on topic '{}'", unit, topic);
                            return;
                        }
                        SensorConfiguration sensor = sensorOpt.get();
                        processTelemetryData(sensor, payload);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("Error processing MQTT message on topic: " + topic, e);
        }
    }

    private void processTelemetryData(SensorConfiguration sensor, String payload) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> data = objectMapper.readValue(payload, Map.class);

        Long timestamp = null;
        if (data.containsKey("timestamp")) {
            Object tsObj = data.get("timestamp");
            if (tsObj instanceof Number) {
                timestamp = ((Number) tsObj).longValue();
            }
        }
        if (timestamp == null) {
            timestamp = System.currentTimeMillis();
        }

        LocalDateTime recordedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), java.time.ZoneOffset.UTC);

        @SuppressWarnings("unchecked")
        Map<String, Object> metrics = (Map<String, Object>) data.get("metrics");
        if (metrics == null || metrics.isEmpty()) {
            return;
        }

        // Resolve Laboratory context
        Laboratory lab = sensor.getLaboratory();
        if (lab == null) {
            String name = sensor.getSensorName();
            String[] parts = name.split(" - ");
            String labName = parts.length > 1 ? parts[parts.length - 1].trim() : "";

            Optional<Laboratory> labOpt = laboratoryRepository.findByName(labName);
            lab = labOpt.orElseGet(() ->
                laboratoryRepository.findAll().stream().findFirst().orElse(null)
            );
        }

        if (lab == null) {
            logger.warn("Could not find any Laboratory to associate telemetry for sensor '{}'", sensor.getSensorName());
            return;
        }

        // Process each metric key-value pair using the MetricType catalog
        for (Map.Entry<String, Object> entry : metrics.entrySet()) {
            String key = entry.getKey();
            Double val = null;
            if (entry.getValue() instanceof Number) {
                val = ((Number) entry.getValue()).doubleValue();
            }
            if (val == null) continue;

            // Resolve MetricType from catalog — reject unknown keys
            Optional<MetricType> metricTypeOpt = metricTypeRepository.findByKey(key);
            if (metricTypeOpt.isEmpty()) {
                logger.warn("Unknown metric key '{}' from sensor '{}' — ignoring. Register it in metric_types first.", key, sensor.getSensorName());
                continue;
            }
            MetricType metricType = metricTypeOpt.get();

            // 1. Save historical reading with FK to MetricType
            SensorReading reading = new SensorReading();
            reading.setSensorConfiguration(sensor);
            reading.setLaboratory(lab);
            reading.setMetricType(metricType);
            reading.setMetricValue(val);
            reading.setRecordedAt(recordedAt);
            sensorReadingRepository.save(reading);

            // 2. Update real-time metrics for Dashboard display
            updateRealTimeLabMetric(lab, metricType, val, sensor);
        }

        // Update Sensor Configuration Connection Info
        sensor.setStatus("ACTIVE");
        sensor.setLastConnected(recordedAt);
        sensorConfigurationRepository.save(sensor);
    }

    /**
     * Updates or creates a real-time LabMetric using data from the MetricType catalog.
     * No more contains()-based inference — icon, unit come from the catalog.
     */
    private void updateRealTimeLabMetric(Laboratory lab, MetricType metricType, Double val, SensorConfiguration sensor) {
        String objectType = sensor.getEquipment() != null ? sensor.getEquipment().getName() : "Ambient";
        LabMetric metric = lab.getMetrics().stream()
                .filter(m -> m.getName().equalsIgnoreCase(metricType.getKey()) &&
                             ((m.getObjectType() == null && "Ambient".equalsIgnoreCase(objectType)) ||
                              (m.getObjectType() != null && m.getObjectType().equalsIgnoreCase(objectType))))
                .findFirst()
                .orElseGet(() -> {
                    LabMetric m = new LabMetric();
                    m.setLaboratory(lab);
                    m.setName(metricType.getKey());
                    m.setUnit(metricType.getUnit());
                    m.setIcon(metricType.getIcon());
                    m.setObjectType(objectType);
                    lab.getMetrics().add(m);
                    return m;
                });

        if (metric.getObjectType() == null) {
            metric.setObjectType(objectType);
        }
        metric.setValue(String.format(java.util.Locale.US, "%.2f", val));
        evaluateThresholds(lab, metricType, val, metric, sensor);
        laboratoryRepository.save(lab);
    }

    /**
     * Evaluates thresholds using the LabMetricSubscription relationship.
     * No more contains()-based string matching — uses exact MetricType reference.
     */
    private void evaluateThresholds(Laboratory lab, MetricType metricType, Double val, LabMetric metric, SensorConfiguration sensor) {
        Double minThreshold = null;
        Double maxThreshold = null;
        Double warningThreshold = null;
        String targetName = "";

        // Resolve thresholds
        EquipmentThreshold equip = sensor.getEquipment();
        if (equip != null) {
            minThreshold = equip.getMinThreshold();
            maxThreshold = equip.getMaxThreshold();
            warningThreshold = equip.getWarningAt();
            targetName = "equipment " + equip.getName();
        } else if (sensor.getMinThreshold() != null || sensor.getMaxThreshold() != null) {
            minThreshold = sensor.getMinThreshold();
            maxThreshold = sensor.getMaxThreshold();
            warningThreshold = sensor.getWarningThreshold();
            targetName = "sensor " + sensor.getSensorName();
        } else if (lab.getMetricSubscriptions() != null) {
            Optional<LabMetricSubscription> subscriptionOpt = lab.getMetricSubscriptions().stream()
                    .filter(sub -> sub.getMetricType().getId().equals(metricType.getId()) && sub.isActive())
                    .findFirst();
            if (subscriptionOpt.isPresent()) {
                LabMetricSubscription sub = subscriptionOpt.get();
                minThreshold = sub.getMinThreshold();
                maxThreshold = sub.getMaxThreshold();
                targetName = "laboratory ambient";
            }
        }

        if (minThreshold == null && maxThreshold == null) {
            metric.setStatus("NORMAL");
            return;
        }

        boolean criticalBreach = false;
        boolean warningBreach = false;
        double thresholdVal = 0.0;

        if (minThreshold != null && val < minThreshold) {
            criticalBreach = true;
            thresholdVal = minThreshold;
        }
        if (maxThreshold != null && val > maxThreshold) {
            criticalBreach = true;
            thresholdVal = maxThreshold;
        }
        if (!criticalBreach && warningThreshold != null) {
            if (val >= warningThreshold) {
                warningBreach = true;
                thresholdVal = warningThreshold;
            }
        }

        boolean wasCritical = "CRITICAL".equalsIgnoreCase(metric.getStatus());
        boolean wasWarning = "WARNING".equalsIgnoreCase(metric.getStatus());

        if (criticalBreach) {
            metric.setStatus("CRITICAL");
            metric.setThreshold(thresholdVal);
            lab.setOverallStatus("CRITICAL");
            lab.setStatus("Critical");

            if (!wasCritical) {
                // Generate a global Alert
                Alert alert = new Alert();
                alert.setLaboratory(lab);
                alert.setSensorConfiguration(sensor);
                alert.setTitle(metricType.getDisplayName() + " Critical Threshold Exceeded");
                alert.setDescription("Sensor " + sensor.getSensorName() + " detected a critical reading of " + String.format("%.2f", val) + metricType.getUnit() + " on " + targetName + ".");
                alert.setSeverity("CRITICAL");
                alert.setStatus("ACTIVE");
                alert.setLabName(lab.getName());
                alert.setTimeAgo("Just now");

                alert = alertRepository.save(alert);

                // Save history record for critical telemetry alert
                var record = new HistoryRecord();
                record.setLaboratory(lab);
                record.setName(alert.getTitle());
                record.setDescription(alert.getDescription());
                record.setOccurredAt(LocalDateTime.now(java.time.ZoneOffset.UTC));
                record.setEventType("incident");
                record.setSeverity("Critical");
                record.setStatus("Active");
                historyRecordRepository.save(record);

                // Populate Alert metrics
                List<AlertMetric> alertMetrics = new java.util.ArrayList<>();
                
                AlertMetric currentValMetric = new AlertMetric();
                currentValMetric.setAlert(alert);
                currentValMetric.setLabel("currentValue");
                currentValMetric.setValue(String.format(java.util.Locale.US, "%.2f", val) + metricType.getUnit());
                alertMetrics.add(currentValMetric);

                AlertMetric thresholdMetric = new AlertMetric();
                thresholdMetric.setAlert(alert);
                thresholdMetric.setLabel("threshold");
                thresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", thresholdVal) + metricType.getUnit());
                alertMetrics.add(thresholdMetric);

                if (minThreshold != null) {
                    AlertMetric minThresholdMetric = new AlertMetric();
                    minThresholdMetric.setAlert(alert);
                    minThresholdMetric.setLabel("minThreshold");
                    minThresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", minThreshold) + metricType.getUnit());
                    alertMetrics.add(minThresholdMetric);
                }

                if (maxThreshold != null) {
                    AlertMetric maxThresholdMetric = new AlertMetric();
                    maxThresholdMetric.setAlert(alert);
                    maxThresholdMetric.setLabel("maxThreshold");
                    maxThresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", maxThreshold) + metricType.getUnit());
                    alertMetrics.add(maxThresholdMetric);
                }

                AlertMetric exceededMetric = new AlertMetric();
                exceededMetric.setAlert(alert);
                exceededMetric.setLabel("exceededBy");
                double diff = Math.abs(val - thresholdVal);
                exceededMetric.setValue("+" + String.format(java.util.Locale.US, "%.2f", diff) + metricType.getUnit());
                alertMetrics.add(exceededMetric);

                AlertMetric sensorTypeMetric = new AlertMetric();
                sensorTypeMetric.setAlert(alert);
                sensorTypeMetric.setLabel("sensorType");
                sensorTypeMetric.setValue(sensor.getType() != null ? sensor.getType() : "NTC Thermistor");
                alertMetrics.add(sensorTypeMetric);

                AlertMetric lastCalMetric = new AlertMetric();
                lastCalMetric.setAlert(alert);
                lastCalMetric.setLabel("lastCalibration");
                lastCalMetric.setValue(sensor.getCalibrationDate() != null ? sensor.getCalibrationDate().toString() : "N/A");
                alertMetrics.add(lastCalMetric);

                AlertMetric signalMetric = new AlertMetric();
                signalMetric.setAlert(alert);
                signalMetric.setLabel("signalStrength");
                signalMetric.setValue("98%");
                alertMetrics.add(signalMetric);

                AlertMetric statusMetric = new AlertMetric();
                statusMetric.setAlert(alert);
                statusMetric.setLabel("networkStatus");
                statusMetric.setValue(sensor.getStatus() != null ? sensor.getStatus() : "ONLINE");
                alertMetrics.add(statusMetric);

                // Resolve matching automation rule from database
                Optional<AutomationRule> triggeredRuleOpt = automationRuleRepository.findAll().stream()
                        .filter(rule -> rule.isActive()
                                && rule.getWorkspaceId() != null
                                && rule.getWorkspaceId().equals(lab.getWorkspace().getId())
                                && rule.getTriggerMetric() != null
                                && rule.getTriggerMetric().equalsIgnoreCase(metricType.getKey())
                                && ("all".equalsIgnoreCase(rule.getScope()) 
                                    || ("specific".equalsIgnoreCase(rule.getScope()) 
                                        && rule.getSpecificLab() != null 
                                        && rule.getSpecificLab().getId().equals(lab.getId()))))
                        .findFirst();

                String ruleName = "None";
                String ruleStatus = "Inactive";
                String ruleDesc = "None";

                if (triggeredRuleOpt.isPresent()) {
                    AutomationRule triggeredRule = triggeredRuleOpt.get();
                    triggeredRule.setLastTriggered(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC));
                    automationRuleRepository.save(triggeredRule);

                    ruleName = triggeredRule.getName();
                    ruleStatus = "Running";
                    ruleDesc = triggeredRule.getActions() != null && !triggeredRule.getActions().isEmpty()
                            ? String.join(", ", triggeredRule.getActions())
                            : "No actions specified";
                }

                AlertMetric ruleNameMetric = new AlertMetric();
                ruleNameMetric.setAlert(alert);
                ruleNameMetric.setLabel("automationRuleName");
                ruleNameMetric.setValue(ruleName);
                alertMetrics.add(ruleNameMetric);

                AlertMetric ruleStatusMetric = new AlertMetric();
                ruleStatusMetric.setAlert(alert);
                ruleStatusMetric.setLabel("automationRuleStatus");
                ruleStatusMetric.setValue(ruleStatus);
                alertMetrics.add(ruleStatusMetric);

                AlertMetric ruleDescMetric = new AlertMetric();
                ruleDescMetric.setAlert(alert);
                ruleDescMetric.setLabel("automationRuleDesc");
                ruleDescMetric.setValue(ruleDesc);
                alertMetrics.add(ruleDescMetric);

                alert.getMetrics().addAll(alertMetrics);
                alertRepository.save(alert);

                // Add to lab alerts
                LabAlert labAlert = new LabAlert();
                labAlert.setLaboratory(lab);
                labAlert.setTitle(alert.getTitle());
                labAlert.setSource("Sensor " + sensor.getSensorName());
                labAlert.setSeverity("CRITICAL");
                labAlert.setTimeAgo("Just now");
                labAlert.setAlertId(alert.getId());
                lab.getAlerts().add(0, labAlert);

                sendEmailNotifications(lab, alert, "CRITICAL", sensor.getSensorName(), String.format(java.util.Locale.US, "%.2f", val) + metricType.getUnit(), String.format(java.util.Locale.US, "%.2f", thresholdVal) + metricType.getUnit());
            }
        } else if (warningBreach) {
            metric.setStatus("WARNING");
            metric.setThreshold(thresholdVal);
            if (!"CRITICAL".equalsIgnoreCase(lab.getOverallStatus())) {
                lab.setOverallStatus("WARNING");
                lab.setStatus("Warning");
            }

            if (!wasWarning && !wasCritical) {
                // Generate a warning Alert
                Alert alert = new Alert();
                alert.setLaboratory(lab);
                alert.setSensorConfiguration(sensor);
                alert.setTitle(metricType.getDisplayName() + " Warning Level Breached");
                alert.setDescription("Sensor " + sensor.getSensorName() + " detected a warning reading of " + String.format("%.2f", val) + metricType.getUnit() + " on " + targetName + ".");
                alert.setSeverity("WARNING");
                alert.setStatus("ACTIVE");
                alert.setLabName(lab.getName());
                alert.setTimeAgo("Just now");

                alert = alertRepository.save(alert);

                // Save history record for warning telemetry alert
                var record = new HistoryRecord();
                record.setLaboratory(lab);
                record.setName(alert.getTitle());
                record.setDescription(alert.getDescription());
                record.setOccurredAt(LocalDateTime.now(java.time.ZoneOffset.UTC));
                record.setEventType("incident");
                record.setSeverity("Warning");
                record.setStatus("Active");
                historyRecordRepository.save(record);

                List<AlertMetric> alertMetrics = new java.util.ArrayList<>();
                
                AlertMetric currentValMetric = new AlertMetric();
                currentValMetric.setAlert(alert);
                currentValMetric.setLabel("currentValue");
                currentValMetric.setValue(String.format(java.util.Locale.US, "%.2f", val) + metricType.getUnit());
                alertMetrics.add(currentValMetric);

                AlertMetric thresholdMetric = new AlertMetric();
                thresholdMetric.setAlert(alert);
                thresholdMetric.setLabel("threshold");
                thresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", thresholdVal) + metricType.getUnit());
                alertMetrics.add(thresholdMetric);

                if (minThreshold != null) {
                    AlertMetric minThresholdMetric = new AlertMetric();
                    minThresholdMetric.setAlert(alert);
                    minThresholdMetric.setLabel("minThreshold");
                    minThresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", minThreshold) + metricType.getUnit());
                    alertMetrics.add(minThresholdMetric);
                }

                if (maxThreshold != null) {
                    AlertMetric maxThresholdMetric = new AlertMetric();
                    maxThresholdMetric.setAlert(alert);
                    maxThresholdMetric.setLabel("maxThreshold");
                    maxThresholdMetric.setValue(String.format(java.util.Locale.US, "%.2f", maxThreshold) + metricType.getUnit());
                    alertMetrics.add(maxThresholdMetric);
                }

                alert.getMetrics().addAll(alertMetrics);
                alertRepository.save(alert);

                LabAlert labAlert = new LabAlert();
                labAlert.setLaboratory(lab);
                labAlert.setTitle(alert.getTitle());
                labAlert.setSource("Sensor " + sensor.getSensorName());
                labAlert.setSeverity("WARNING");
                labAlert.setTimeAgo("Just now");
                labAlert.setAlertId(alert.getId());
                lab.getAlerts().add(0, labAlert);

                sendEmailNotifications(lab, alert, "WARNING", sensor.getSensorName(), String.format(java.util.Locale.US, "%.2f", val) + metricType.getUnit(), String.format(java.util.Locale.US, "%.2f", thresholdVal) + metricType.getUnit());
            }
        } else {
            metric.setStatus("NORMAL");
            // Reset lab overall status if no other metrics are critical or warning
            boolean anyOtherCritical = lab.getMetrics().stream()
                    .anyMatch(m -> "CRITICAL".equalsIgnoreCase(m.getStatus()) && !m.getName().equalsIgnoreCase(metricType.getKey()));
            boolean anyOtherWarning = lab.getMetrics().stream()
                    .anyMatch(m -> "WARNING".equalsIgnoreCase(m.getStatus()) && !m.getName().equalsIgnoreCase(metricType.getKey()));
            if (!anyOtherCritical && !anyOtherWarning) {
                lab.setOverallStatus("OPERATIONAL");
                lab.setStatus("Operational");
            } else if (!anyOtherCritical) {
                lab.setOverallStatus("WARNING");
                lab.setStatus("Warning");
            }
        }
    }

    private void sendEmailNotifications(Laboratory lab, Alert alert, String severity, String sensorName, String valueStr, String thresholdStr) {
        try {
            NotificationPreferences prefs = lab.getNotificationPreferences();
            if (prefs == null || !prefs.isEmail()) {
                return;
            }
            if ("WARNING".equalsIgnoreCase(severity) && prefs.isCriticalOnly()) {
                return;
            }
            
            var workspaceAccessList = userWorkspaceAccessRepository.findByWorkspaceId(lab.getWorkspace().getId());
            if (workspaceAccessList != null && !workspaceAccessList.isEmpty()) {
                for (var access : workspaceAccessList) {
                    if (access.getUserProfile() != null && access.getUserProfile().getEmail() != null) {
                        emailService.sendAlertEmail(
                            access.getUserProfile().getEmail(),
                            access.getUserProfile().getFullName(),
                            lab.getName(),
                            alert.getTitle(),
                            alert.getDescription(),
                            severity,
                            sensorName,
                            valueStr,
                            thresholdStr
                        );
                    }
                }
            } else {
                logger.warn("No workspace users found to notify via email for lab {}", lab.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to trigger email notifications for alert: {}", alert.getTitle(), e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // No-op (we only subscribe)
    }
}
