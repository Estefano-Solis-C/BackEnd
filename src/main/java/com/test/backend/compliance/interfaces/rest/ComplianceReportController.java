package com.test.backend.compliance.interfaces.rest;

import com.test.backend.compliance.domain.model.queries.GetComplianceReportsByLaboratoryIdQuery;
import com.test.backend.compliance.domain.services.ComplianceReportQueryService;
import com.test.backend.compliance.interfaces.rest.resources.ComplianceReportResource;
import com.test.backend.compliance.interfaces.rest.transform.ComplianceReportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Compliance Reports", description = "Compliance report management API")
@RestController
@RequestMapping("/api/v1/laboratories/{laboratoryId}/compliance-reports")
public class ComplianceReportController {

    private final ComplianceReportQueryService complianceReportQueryService;

    public ComplianceReportController(ComplianceReportQueryService complianceReportQueryService) {
        this.complianceReportQueryService = complianceReportQueryService;
    }

    @GetMapping
    @Operation(summary = "Get compliance reports by laboratory ID")
    public ResponseEntity<List<ComplianceReportResource>> getComplianceReportsByLaboratoryId(@PathVariable Long laboratoryId) {
        var query = new GetComplianceReportsByLaboratoryIdQuery(laboratoryId);
        var reports = complianceReportQueryService.handle(query);
        var resources = reports.stream()
                .map(ComplianceReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
