package com.test.backend.compliance.interfaces.rest.transform;

import com.test.backend.compliance.domain.model.aggregates.ComplianceReport;
import com.test.backend.compliance.interfaces.rest.resources.ComplianceReportResource;

public class ComplianceReportResourceFromEntityAssembler {
    public static ComplianceReportResource toResourceFromEntity(ComplianceReport entity) {
        return new ComplianceReportResource(
            entity.getId(),
            entity.getName(),
            entity.getType(),
            entity.getPeriod(),
            entity.getSize(),
            entity.getGeneratedAt(),
            entity.getStatus()
        );
    }
}
