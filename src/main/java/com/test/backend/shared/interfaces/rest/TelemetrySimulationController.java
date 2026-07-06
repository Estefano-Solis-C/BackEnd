package com.test.backend.shared.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Tag(name = "Telemetry Simulation", description = "Mock telemetry publisher API for development")
@RestController
@RequestMapping("/api/v1/telemetry/simulate")
public class TelemetrySimulationController {

    private final MqttClient mqttClient;

    public TelemetrySimulationController(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @PostMapping
    @Operation(summary = "Publish a mock telemetry payload to the MQTT broker")
    public ResponseEntity<Map<String, String>> simulateTelemetry(@RequestBody Map<String, Object> request) {
        try {
            String unit = request.getOrDefault("unit", "°C").toString();
            String metricName = request.getOrDefault("metric", "temperature").toString();
            double value = Double.parseDouble(request.getOrDefault("value", "5.4").toString());

            String topic = "safelab/" + unit + "/data";
            String payload = String.format(java.util.Locale.US, "{\"timestamp\":%d,\"metrics\":{\"%s\":%.2f}}",
                    System.currentTimeMillis(), metricName, value);

            MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
            message.setQos(1);
            mqttClient.publish(topic, message);

            return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "topic", topic,
                "payload", payload
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "ERROR",
                "message", e.getMessage()
            ));
        }
    }
}
