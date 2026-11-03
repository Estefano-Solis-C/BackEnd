package com.test.backend.alerts.domain.model.entities;

import com.test.backend.alerts.domain.model.aggregates.Incident;
import com.test.backend.shared.domain.model.entities.AuditableModel;
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
@Table(name = "corrective_actions")
public class CorrectiveAction extends AuditableModel {

    @OneToOne
    @JoinColumn(name = "incident_id", unique = true, nullable = false)
    private Incident incident;

    @Column(name = "technician_id", nullable = false)
    private Long technicianId;

    @Lob
    @Column(name = "action_taken", columnDefinition = "TEXT")
    private String actionTaken;

    @Column(name = "performed_at")
    private LocalDateTime performedAt;

    public CorrectiveAction(Incident incident, Long technicianId, String actionTaken) {
        if (actionTaken == null || actionTaken.trim().length() < 10) {
            throw new IllegalArgumentException("Action taken description must contain at least 10 characters for regulatory audit compliance");
        }
        this.incident = incident;
        this.technicianId = technicianId != null ? technicianId : 1L;
        this.actionTaken = actionTaken.trim();
        this.performedAt = LocalDateTime.now();
    }
}
