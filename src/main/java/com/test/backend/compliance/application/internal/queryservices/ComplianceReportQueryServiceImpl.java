package com.test.backend.compliance.application.internal.queryservices;

import com.test.backend.compliance.domain.model.aggregates.ComplianceReport;
import com.test.backend.compliance.domain.model.queries.GetComplianceReportsByLaboratoryIdQuery;
import com.test.backend.compliance.domain.services.ComplianceReportQueryService;
import com.test.backend.compliance.infrastructure.persistence.jpa.repositories.ComplianceReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceReportQueryServiceImpl implements ComplianceReportQueryService {

    private final ComplianceReportRepository complianceReportRepository;

    public ComplianceReportQueryServiceImpl(ComplianceReportRepository complianceReportRepository) {
        this.complianceReportRepository = complianceReportRepository;
    }

    @Override
    public List<ComplianceReport> handle(GetComplianceReportsByLaboratoryIdQuery query) {
        return complianceReportRepository.findByLaboratoryId(query.laboratoryId());
    }
}
