package com.test.backend.shared.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "System Status", description = "System and Infrastructure Health Status API")
@RestController
@RequestMapping("/api/v1/system/status")
public class SystemStatusController {

    private final MqttClient mqttClient;

    public SystemStatusController(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @GetMapping
    @Operation(summary = "Get connection status between backend and MQTT broker", description = "Checks whether the backend is actively connected to the MQTT broker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "System infrastructure and MQTT connection status retrieved successfully")
    })
    public ResponseEntity<Map<String, String>> getSystemStatus() {
        String status = (mqttClient != null && mqttClient.isConnected()) ? "CONNECTED" : "DISCONNECTED";
        return ResponseEntity.ok(Map.of("status", status));
    }
}
