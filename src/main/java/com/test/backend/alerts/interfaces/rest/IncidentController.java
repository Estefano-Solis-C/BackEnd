package com.test.backend.alerts.interfaces.rest;

import com.test.backend.alerts.domain.model.commands.AcknowledgeIncidentCommand;
import com.test.backend.alerts.domain.model.commands.ApplyEscalationCommand;
import com.test.backend.alerts.domain.model.commands.ResolveIncidentCommand;
import com.test.backend.alerts.domain.model.queries.GetIncidentByIdQuery;
import com.test.backend.alerts.domain.model.queries.GetIncidentsQuery;
import com.test.backend.alerts.domain.services.IncidentCommandService;
import com.test.backend.alerts.domain.services.IncidentQueryService;
import com.test.backend.alerts.interfaces.rest.resources.CreateIncidentResource;
import com.test.backend.alerts.interfaces.rest.resources.EscalateIncidentResource;
import com.test.backend.alerts.interfaces.rest.resources.IncidentResource;
import com.test.backend.alerts.interfaces.rest.resources.ResolveIncidentResource;
import com.test.backend.alerts.interfaces.rest.transform.CreateIncidentCommandFromResourceAssembler;
import com.test.backend.alerts.interfaces.rest.transform.IncidentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Incidents", description = "Incident Management and Response API")
@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final IncidentCommandService incidentCommandService;
    private final IncidentQueryService incidentQueryService;

    public IncidentController(IncidentCommandService incidentCommandService, IncidentQueryService incidentQueryService) {
        this.incidentCommandService = incidentCommandService;
        this.incidentQueryService = incidentQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all incidents", description = "Retrieves incidents scoped to user's workspace, optionally filtered by laboratory and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidents retrieved successfully")
    })
    public ResponseEntity<List<IncidentResource>> getIncidents(
            @RequestParam(required = false) Long laboratoryId,
            @RequestParam(required = false) String status
    ) {
        var query = new GetIncidentsQuery(laboratoryId, status);
        var incidents = incidentQueryService.handle(query);
        var resources = incidents.stream()
                .map(IncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get incident by ID", description = "Retrieves a specific incident by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Incident not found")
    })
    public ResponseEntity<IncidentResource> getIncidentById(@PathVariable Long id) {
        var query = new GetIncidentByIdQuery(id);
        var result = incidentQueryService.handle(query);
        return result.map(incident -> ResponseEntity.ok(IncidentResourceFromEntityAssembler.toResourceFromEntity(incident)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create incident", description = "Creates a new critical incident for a laboratory if no active incident is currently open")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incident created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or laboratory not accessible"),
            @ApiResponse(responseCode = "409", description = "Active incident already exists for this laboratory")
    })
    public ResponseEntity<?> createIncident(@RequestBody CreateIncidentResource resource) {
        try {
            var command = CreateIncidentCommandFromResourceAssembler.toCommandFromResource(resource);
            var result = incidentCommandService.handle(command);
            return result.map(incident -> ResponseEntity.status(HttpStatus.CREATED)
                            .body(IncidentResourceFromEntityAssembler.toResourceFromEntity(incident)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge incident", description = "Acknowledges receipt and response responsibility for an open incident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident acknowledged successfully"),
            @ApiResponse(responseCode = "404", description = "Incident not found")
    })
    public ResponseEntity<IncidentResource> acknowledgeIncident(@PathVariable Long id) {
        var command = new AcknowledgeIncidentCommand(id);
        var result = incidentCommandService.handle(command);
        return result.map(incident -> ResponseEntity.ok(IncidentResourceFromEntityAssembler.toResourceFromEntity(incident)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/resolve")
    @Operation(summary = "Resolve incident", description = "Resolves an incident by attaching regulatory corrective action notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident resolved successfully"),
            @ApiResponse(responseCode = "400", description = "Corrective action description does not meet regulatory minimum length"),
            @ApiResponse(responseCode = "404", description = "Incident not found")
    })
    public ResponseEntity<?> resolveIncident(
            @PathVariable Long id,
            @RequestBody ResolveIncidentResource resource
    ) {
        try {
            var command = new ResolveIncidentCommand(id, resource.actionTaken(), resource.technicianId());
            var result = incidentCommandService.handle(command);
            return result.map(incident -> ResponseEntity.ok(IncidentResourceFromEntityAssembler.toResourceFromEntity(incident)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/escalate")
    @Operation(summary = "Escalate incident", description = "Escalates an unaddressed incident to the next supervisory tier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident escalated successfully"),
            @ApiResponse(responseCode = "404", description = "Incident not found")
    })
    public ResponseEntity<IncidentResource> escalateIncident(
            @PathVariable Long id,
            @RequestBody(required = false) EscalateIncidentResource resource
    ) {
        String nextRole = (resource != null && resource.nextRole() != null) ? resource.nextRole() : "SAFETY_MANAGER";
        var command = new ApplyEscalationCommand(id, nextRole);
        var result = incidentCommandService.handle(command);
        return result.map(incident -> ResponseEntity.ok(IncidentResourceFromEntityAssembler.toResourceFromEntity(incident)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
