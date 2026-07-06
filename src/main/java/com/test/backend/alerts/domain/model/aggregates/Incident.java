package com.test.backend.alerts.domain.model.aggregates;

import com.test.backend.alerts.domain.model.entities.CorrectiveAction;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incidents")
public class Incident extends AuditableAbstractAggregateRoot<Incident> {

    @ManyToOne
    @JoinColumn(name = "laboratory_id", nullable = false)
    private Laboratory laboratory;
    private String status;
    @Column(name = "opened_at")
    private java.time.LocalDateTime openedAt;
    @Column(name = "resolved_at")
    private java.time.LocalDateTime resolvedAt;
    @Column(name = "escalation_max_mins")
    private Integer escalationMaxMins;

    @Column(name = "escalation_next_role")
    private String escalationNextRole;

    @OneToOne(mappedBy = "incident", cascade = CascadeType.ALL)
    private CorrectiveAction correctiveAction;
}
