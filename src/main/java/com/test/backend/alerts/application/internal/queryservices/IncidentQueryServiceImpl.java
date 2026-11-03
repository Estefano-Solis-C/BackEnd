package com.test.backend.alerts.application.internal.queryservices;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.queries.GetIncidentByIdQuery;
import com.test.backend.alerts.domain.model.queries.GetIncidentsQuery;
import com.test.backend.alerts.domain.services.IncidentQueryService;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import com.test.backend.alerts.infrastructure.security.JWTSecurityContextAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class IncidentQueryServiceImpl implements IncidentQueryService {

    private final IncidentRepository incidentRepository;
    private final JWTSecurityContextAdapter jwtSecurityContextAdapter;

    public IncidentQueryServiceImpl(
            IncidentRepository incidentRepository,
            JWTSecurityContextAdapter jwtSecurityContextAdapter
    ) {
        this.incidentRepository = incidentRepository;
        this.jwtSecurityContextAdapter = jwtSecurityContextAdapter;
    }

    @Override
    public List<Incident> handle(GetIncidentsQuery query) {
        var workspaceIdOpt = jwtSecurityContextAdapter.getCurrentWorkspaceId();
        boolean isScoped = workspaceIdOpt.isPresent() && !jwtSecurityContextAdapter.isCurrentUserAdmin();

        if (isScoped) {
            Long workspaceId = workspaceIdOpt.get();
            if (query.laboratoryId() != null && query.status() != null) {
                return incidentRepository.findByLaboratoryIdAndLaboratoryWorkspaceIdAndStatus(query.laboratoryId(), workspaceId, query.status());
            }
            if (query.laboratoryId() != null) {
                return incidentRepository.findByLaboratoryIdAndLaboratoryWorkspaceId(query.laboratoryId(), workspaceId);
            }
            if (query.status() != null) {
                return incidentRepository.findByLaboratoryWorkspaceIdAndStatus(workspaceId, query.status());
            }
            return incidentRepository.findByLaboratoryWorkspaceId(workspaceId);
        }

        if (query.laboratoryId() != null) {
            var list = incidentRepository.findByLaboratoryId(query.laboratoryId());
            if (query.status() != null) {
                return list.stream().filter(i -> query.status().equalsIgnoreCase(i.getStatus())).toList();
            }
            return list;
        }

        if (query.status() != null) {
            return incidentRepository.findAll().stream()
                    .filter(i -> query.status().equalsIgnoreCase(i.getStatus()))
                    .toList();
        }

        return incidentRepository.findAll();
    }

    @Override
    public Optional<Incident> handle(GetIncidentByIdQuery query) {
        var workspaceIdOpt = jwtSecurityContextAdapter.getCurrentWorkspaceId();
        if (workspaceIdOpt.isPresent() && !jwtSecurityContextAdapter.isCurrentUserAdmin()) {
            return incidentRepository.findByIdAndLaboratoryWorkspaceId(query.incidentId(), workspaceIdOpt.get());
        }
        return incidentRepository.findById(query.incidentId());
    }
}
