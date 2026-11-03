package com.test.backend.alerts.domain.model.aggregates;

import com.test.backend.alerts.domain.model.entities.CorrectiveAction;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "severity")
    private String severity;

    private String status;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "escalation_max_mins")
    private Integer escalationMaxMins;

    @Column(name = "escalation_next_role")
    private String escalationNextRole;

    @OneToOne(mappedBy = "incident", cascade = CascadeType.ALL)
    private CorrectiveAction correctiveAction;

    public Incident(Laboratory laboratory, String title, String description, String severity, Integer escalationMaxMins, String escalationNextRole) {
        this.laboratory = laboratory;
        this.title = title;
        this.description = description;
        this.severity = severity != null ? severity : "CRITICAL";
        this.status = "OPEN";
        this.openedAt = LocalDateTime.now();
        this.escalationMaxMins = escalationMaxMins != null ? escalationMaxMins : 15;
        this.escalationNextRole = escalationNextRole != null ? escalationNextRole : "COORDINATOR";
    }

    public void acknowledge() {
        if ("RESOLVED".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Cannot acknowledge an already resolved incident");
        }
        this.status = "ACKNOWLEDGED";
    }

    public void resolve(CorrectiveAction action) {
        if ("RESOLVED".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Incident is already resolved");
        }
        this.status = "RESOLVED";
        this.resolvedAt = LocalDateTime.now();
        this.correctiveAction = action;
        action.setIncident(this);
    }

    public void escalate(String nextRole) {
        if ("RESOLVED".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Cannot escalate a resolved incident");
        }
        this.status = "ESCALATED";
        if (nextRole != null && !nextRole.isBlank()) {
            this.escalationNextRole = nextRole;
        }
    }
}
