package com.test.backend.labs.interfaces.rest;

import com.test.backend.labs.domain.model.commands.DeleteLaboratoryCommand;
import com.test.backend.labs.domain.model.queries.GetAllLaboratoriesQuery;
import com.test.backend.labs.domain.model.queries.GetLaboratoryByIdQuery;
import com.test.backend.labs.domain.services.LaboratoryCommandService;
import com.test.backend.labs.domain.services.LaboratoryQueryService;
import com.test.backend.labs.interfaces.rest.resources.CreateLaboratoryResource;
import com.test.backend.labs.interfaces.rest.resources.LaboratoryResource;
import com.test.backend.labs.interfaces.rest.resources.UpdateLaboratoryResource;
import com.test.backend.labs.interfaces.rest.transform.CreateLaboratoryCommandFromResourceAssembler;
import com.test.backend.labs.interfaces.rest.transform.LaboratoryResourceFromEntityAssembler;
import com.test.backend.labs.interfaces.rest.transform.UpdateLaboratoryCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Laboratories", description = "Laboratory management API")
@RestController
@RequestMapping("/api/v1/laboratories")
public class LaboratoryController {

    private final LaboratoryCommandService laboratoryCommandService;
    private final LaboratoryQueryService laboratoryQueryService;

    public LaboratoryController(LaboratoryCommandService laboratoryCommandService,
                                LaboratoryQueryService laboratoryQueryService) {
        this.laboratoryCommandService = laboratoryCommandService;
        this.laboratoryQueryService = laboratoryQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all laboratories")
    public ResponseEntity<List<LaboratoryResource>> getAllLaboratories() {
        var query = new GetAllLaboratoriesQuery();
        var laboratories = laboratoryQueryService.handle(query);
        var resources = laboratories.stream()
                .map(LaboratoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get laboratory by ID")
    public ResponseEntity<LaboratoryResource> getLaboratoryById(@PathVariable Long id) {
        var query = new GetLaboratoryByIdQuery(id);
        var result = laboratoryQueryService.handle(query);
        return result.map(laboratory -> ResponseEntity.ok(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Lab created successfully")
    @Operation(summary = "Create a new laboratory")
    public ResponseEntity<LaboratoryResource> createLaboratory(@RequestBody CreateLaboratoryResource resource) {
        var command = CreateLaboratoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> new ResponseEntity<>(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing laboratory")
    public ResponseEntity<LaboratoryResource> updateLaboratory(@PathVariable Long id, @RequestBody UpdateLaboratoryResource resource) {
        var command = UpdateLaboratoryCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> ResponseEntity.ok(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete laboratory by ID")
    public ResponseEntity<?> deleteLaboratory(@PathVariable Long id) {
        var command = new DeleteLaboratoryCommand(id);
        try {
            laboratoryCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
