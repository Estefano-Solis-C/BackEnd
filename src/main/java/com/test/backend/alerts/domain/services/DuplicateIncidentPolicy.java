package com.test.backend.alerts.domain.services;

import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuplicateIncidentPolicy {

    private final IncidentRepository incidentRepository;

    public DuplicateIncidentPolicy(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public boolean hasActiveIncident(Long laboratoryId) {
        if (laboratoryId == null) {
            return false;
        }
        return incidentRepository.existsByLaboratoryIdAndStatusIn(
                laboratoryId,
                List.of("OPEN", "ACKNOWLEDGED", "ESCALATED")
        );
    }
}
