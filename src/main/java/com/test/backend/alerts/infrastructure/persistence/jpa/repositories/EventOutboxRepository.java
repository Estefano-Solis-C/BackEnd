package com.test.backend.alerts.infrastructure.persistence.jpa.repositories;

import com.test.backend.alerts.domain.model.entities.EventOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventOutboxRepository extends JpaRepository<EventOutbox, Long> {
    List<EventOutbox> findByStatus(String status);
    Optional<EventOutbox> findByEventId(String eventId);
}
