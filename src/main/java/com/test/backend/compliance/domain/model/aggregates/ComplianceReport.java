package com.test.backend.compliance.domain.model.aggregates;

import com.test.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
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
@Table(name = "compliance_reports")
public class ComplianceReport extends AuditableAbstractAggregateRoot<ComplianceReport> {

    @ManyToOne
    @JoinColumn(name = "laboratory_id", nullable = true)
    private Laboratory laboratory;

    private String name;
    private String type;
    private String period;
    private String size;
    private String status;

    @Column(name = "period_start")
    private java.time.LocalDateTime periodStart;

    @Column(name = "period_end")
    private java.time.LocalDateTime periodEnd;

    @Column(name = "digital_signature")
    private String digitalSignature;

    @Column(name = "generated_at")
    private java.time.LocalDateTime generatedAt;

    @ManyToMany
    @JoinTable(
            name = "report_audit_links",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "audit_id")
            )
    private List<AuditTrail> auditTrails = new ArrayList<>();
}
