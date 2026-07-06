package com.test.backend.telemetry.interfaces.rest;

import com.test.backend.labs.domain.model.commands.DeleteLaboratoryCommand;
import com.test.backend.labs.domain.model.queries.GetAllLaboratoriesQuery;
import com.test.backend.labs.domain.model.queries.GetLaboratoryByIdQuery;
import com.test.backend.labs.domain.services.LaboratoryCommandService;
import com.test.backend.labs.domain.services.LaboratoryQueryService;
import com.test.backend.labs.interfaces.rest.resources.CreateLaboratoryResource;
import com.test.backend.labs.interfaces.rest.resources.UpdateLaboratoryResource;
import com.test.backend.labs.interfaces.rest.transform.CreateLaboratoryCommandFromResourceAssembler;
import com.test.backend.labs.interfaces.rest.transform.UpdateLaboratoryCommandFromResourceAssembler;
import com.test.backend.telemetry.interfaces.rest.resources.TelemetryLaboratoryResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Telemetry Laboratories", description = "Telemetry Laboratories View API")
@RestController
@RequestMapping("/api/v1/telemetry/laboratories")
public class TelemetryLaboratoryController {

    private final LaboratoryCommandService laboratoryCommandService;
    private final LaboratoryQueryService laboratoryQueryService;

    public TelemetryLaboratoryController(LaboratoryCommandService laboratoryCommandService,
                                         LaboratoryQueryService laboratoryQueryService) {
        this.laboratoryCommandService = laboratoryCommandService;
        this.laboratoryQueryService = laboratoryQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all telemetry laboratories")
    public ResponseEntity<List<TelemetryLaboratoryResource>> getAllLaboratories() {
        var query = new GetAllLaboratoriesQuery();
        var laboratories = laboratoryQueryService.handle(query);
        var resources = laboratories.stream()
                .map(this::toTelemetryResource)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get telemetry laboratory by ID")
    public ResponseEntity<TelemetryLaboratoryResource> getLaboratoryById(@PathVariable Long id) {
        var query = new GetLaboratoryByIdQuery(id);
        var result = laboratoryQueryService.handle(query);
        return result.map(laboratory -> ResponseEntity.ok(toTelemetryResource(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new telemetry laboratory")
    public ResponseEntity<TelemetryLaboratoryResource> createLaboratory(@RequestBody CreateLaboratoryResource resource) {
        var command = CreateLaboratoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> new ResponseEntity<>(toTelemetryResource(laboratory), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing telemetry laboratory")
    public ResponseEntity<TelemetryLaboratoryResource> updateLaboratory(@PathVariable Long id, @RequestBody UpdateLaboratoryResource resource) {
        var command = UpdateLaboratoryCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> ResponseEntity.ok(toTelemetryResource(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete telemetry laboratory by ID")
    public ResponseEntity<?> deleteLaboratory(@PathVariable Long id) {
        var command = new DeleteLaboratoryCommand(id);
        try {
            laboratoryCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private TelemetryLaboratoryResource toTelemetryResource(com.test.backend.labs.domain.model.aggregates.Laboratory lab) {
        Double temperature = null;
        if (lab.getMetrics() != null) {
            for (var m : lab.getMetrics()) {
                if (m.getName().toLowerCase().contains("temp")) {
                    try {
                        String val = m.getValue().replace(',', '.');
                        temperature = Double.parseDouble(val);
                    } catch (Exception ignored) {}
                }
            }
        }
        
        String status = "NORMAL";
        if (lab.getOverallStatus() != null) {
            String os = lab.getOverallStatus().toUpperCase();
            if (os.contains("CRITICAL") || os.contains("ALERT")) {
                status = "ALERT";
            } else if (os.contains("WARNING")) {
                status = "WARNING";
            }
        }

        return new TelemetryLaboratoryResource(
                lab.getId(),
                lab.getName(),
                lab.getType(),
                temperature,
                status
        );
    }
}
