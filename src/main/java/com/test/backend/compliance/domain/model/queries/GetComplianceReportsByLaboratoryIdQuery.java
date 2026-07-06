package com.test.backend.compliance.domain.model.queries;

public record GetComplianceReportsByLaboratoryIdQuery(Long laboratoryId) {
    public GetComplianceReportsByLaboratoryIdQuery {
        if (laboratoryId == null) {
            throw new IllegalArgumentException("laboratoryId cannot be null");
        }
    }
}
