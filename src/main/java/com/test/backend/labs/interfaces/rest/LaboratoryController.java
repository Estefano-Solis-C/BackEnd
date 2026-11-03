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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all laboratories", description = "Retrieves all laboratories registered in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratories retrieved successfully")
    })
    public ResponseEntity<List<LaboratoryResource>> getAllLaboratories() {
        var query = new GetAllLaboratoriesQuery();
        var laboratories = laboratoryQueryService.handle(query);
        var resources = laboratories.stream()
                .map(LaboratoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get laboratory by ID", description = "Retrieves a laboratory by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratory retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Laboratory not found")
    })
    public ResponseEntity<LaboratoryResource> getLaboratoryById(@PathVariable Long id) {
        var query = new GetLaboratoryByIdQuery(id);
        var result = laboratoryQueryService.handle(query);
        return result.map(laboratory -> ResponseEntity.ok(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new laboratory", description = "Creates and registers a new laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Laboratory created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid laboratory data provided")
    })
    public ResponseEntity<LaboratoryResource> createLaboratory(@RequestBody CreateLaboratoryResource resource) {
        var command = CreateLaboratoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> new ResponseEntity<>(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing laboratory", description = "Updates the attributes and configuration of an existing laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Laboratory updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid laboratory data provided"),
            @ApiResponse(responseCode = "404", description = "Laboratory not found")
    })
    public ResponseEntity<LaboratoryResource> updateLaboratory(@PathVariable Long id, @RequestBody UpdateLaboratoryResource resource) {
        var command = UpdateLaboratoryCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = laboratoryCommandService.handle(command);
        return result.map(laboratory -> ResponseEntity.ok(LaboratoryResourceFromEntityAssembler.toResourceFromEntity(laboratory)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete laboratory by ID", description = "Deletes a laboratory by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Laboratory deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Laboratory not found")
    })
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
