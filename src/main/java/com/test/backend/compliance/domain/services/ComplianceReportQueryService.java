package com.test.backend.compliance.domain.services;

import com.test.backend.compliance.domain.model.aggregates.ComplianceReport;
import com.test.backend.compliance.domain.model.queries.GetComplianceReportsByLaboratoryIdQuery;

import java.util.List;

public interface ComplianceReportQueryService {
    List<ComplianceReport> handle(GetComplianceReportsByLaboratoryIdQuery query);
}
