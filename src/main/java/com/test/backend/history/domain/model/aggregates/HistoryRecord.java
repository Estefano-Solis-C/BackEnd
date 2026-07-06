package com.test.backend.history.domain.model.aggregates;

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
@Table(name = "history_records")
public class HistoryRecord extends AuditableAbstractAggregateRoot<HistoryRecord> {
    @ManyToOne
    @JoinColumn(name = "laboratory_id", nullable = true)
    private Laboratory laboratory;
    private String name;
    private String description;
    @Column(name = "occurred_at")
    private java.time.LocalDateTime occurredAt;
    @Column(name = "event_type")
    private String eventType;
    private String severity;
    private String status;

    public HistoryRecord(com.test.backend.history.domain.model.commands.CreateHistoryRecordCommand command, Laboratory laboratory) {
        this.laboratory = laboratory;
        this.name = command.name();
        this.description = command.description();
        this.occurredAt = command.occurredAt();
        this.eventType = command.eventType();
        this.severity = command.severity();
        this.status = command.status();
    }

    public HistoryRecord updateFrom(com.test.backend.history.domain.model.commands.UpdateHistoryRecordCommand command, Laboratory laboratory) {
        this.laboratory = laboratory;
        this.name = command.name();
        this.description = command.description();
        this.occurredAt = command.occurredAt();
        this.eventType = command.eventType();
        this.severity = command.severity();
        this.status = command.status();
        return this;
    }
}
