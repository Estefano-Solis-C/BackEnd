package com.test.backend.compliance.domain.model.aggregates;

import com.test.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "audit_trails",
    indexes = {
        @Index(name = "idx_audit_trails_target_id", columnList = "target_id")
    }
)
public class AuditTrail extends AuditableAbstractAggregateRoot<AuditTrail> {

    @Column(name = "target_id")
    private String targetId;

    private String action;

    @Column(name = "actor")
    private String actor;

    @Column(name = "previous_state")
    private String previousState;

    @Column(name = "new_state")
    private String newState;

    @Column(name = "details", length = 1000)
    private String details;

    private String signature;

    @ManyToMany(mappedBy = "auditTrails")
    private List<ComplianceReport> complianceReports = new ArrayList<>();

    public AuditTrail(String targetId, String action, String actor, String previousState, String newState, String details, String signature) {
        this.targetId = targetId;
        this.action = action;
        this.actor = actor;
        this.previousState = previousState;
        this.newState = newState;
        this.details = details;
        this.signature = signature;
    }
}
