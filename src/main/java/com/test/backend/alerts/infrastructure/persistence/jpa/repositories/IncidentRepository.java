package com.test.backend.alerts.infrastructure.persistence.jpa.repositories;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByLaboratoryId(Long laboratoryId);
    List<Incident> findByLaboratoryWorkspaceId(Long workspaceId);
    List<Incident> findByLaboratoryWorkspaceIdAndStatus(Long workspaceId, String status);
    List<Incident> findByLaboratoryIdAndLaboratoryWorkspaceId(Long laboratoryId, Long workspaceId);
    List<Incident> findByLaboratoryIdAndLaboratoryWorkspaceIdAndStatus(Long laboratoryId, Long workspaceId, String status);
    Optional<Incident> findByIdAndLaboratoryWorkspaceId(Long id, Long workspaceId);
    boolean existsByLaboratoryIdAndStatusIn(Long laboratoryId, List<String> statuses);
}
