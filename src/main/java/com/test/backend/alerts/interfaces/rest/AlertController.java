package com.test.backend.alerts.interfaces.rest;

import com.test.backend.alerts.domain.model.commands.DeleteAlertCommand;
import com.test.backend.alerts.domain.model.queries.GetAllAlertsQuery;
import com.test.backend.alerts.domain.model.queries.GetAlertByIdQuery;
import com.test.backend.alerts.domain.services.AlertCommandService;
import com.test.backend.alerts.domain.services.AlertQueryService;
import com.test.backend.alerts.interfaces.rest.resources.CreateAlertResource;
import com.test.backend.alerts.interfaces.rest.resources.AlertResource;
import com.test.backend.alerts.interfaces.rest.resources.UpdateAlertResource;
import com.test.backend.alerts.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.test.backend.alerts.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.test.backend.alerts.interfaces.rest.transform.UpdateAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alerts", description = "Alert management API")
@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all alerts", description = "Retrieves all alerts registered in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully")
    })
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var query = new GetAllAlertsQuery();
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID", description = "Retrieves an alert by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long id) {
        var query = new GetAlertByIdQuery(id);
        var result = alertQueryService.handle(query);
        return result.map(alert -> ResponseEntity.ok(AlertResourceFromEntityAssembler.toResourceFromEntity(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new alert", description = "Creates and registers a new alert event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alert created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid alert data provided")
    })
    public ResponseEntity<AlertResource> createAlert(@RequestBody CreateAlertResource resource) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertCommandService.handle(command);
        return result.map(alert -> new ResponseEntity<>(AlertResourceFromEntityAssembler.toResourceFromEntity(alert), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing alert", description = "Updates the attributes of an existing alert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid alert data provided"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<AlertResource> updateAlert(@PathVariable Long id, @RequestBody UpdateAlertResource resource) {
        var command = UpdateAlertCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = alertCommandService.handle(command);
        return result.map(alert -> ResponseEntity.ok(AlertResourceFromEntityAssembler.toResourceFromEntity(alert)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete alert by ID", description = "Deletes an alert by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alert deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<?> deleteAlert(@PathVariable Long id) {
        var command = new DeleteAlertCommand(id);
        try {
            alertCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
