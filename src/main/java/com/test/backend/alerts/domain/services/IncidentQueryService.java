package com.test.backend.alerts.domain.services;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.alerts.domain.model.queries.GetIncidentByIdQuery;
import com.test.backend.alerts.domain.model.queries.GetIncidentsQuery;

import java.util.List;
import java.util.Optional;

public interface IncidentQueryService {
    List<Incident> handle(GetIncidentsQuery query);
    Optional<Incident> handle(GetIncidentByIdQuery query);
}
