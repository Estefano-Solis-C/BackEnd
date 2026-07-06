package com.test.backend.labs.domain.model.aggregates;

import com.test.backend.labs.domain.model.entities.*;
import com.test.backend.labs.domain.model.valueobjets.NotificationPreferences;
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
@Table(name = "laboratories")
public class Laboratory extends AuditableAbstractAggregateRoot<Laboratory> {

    @Column(nullable = false)
    private String name;

    private String type;

    private String status;

    private String building;

    private String floor;

    @Column(name = "lab_code", unique = true, nullable = false)
    private String labCode;

    @Column(name = "room_number")
    private String roomNumber;

    private String description;

    @Column(name = "overall_status")
    private String overallStatus;

    private boolean active;

    @Column(name = "is_live")
    private boolean isLive;

    @Column(name = "last_update")
    private java.time.LocalDateTime lastUpdate;

    @Column(name = "next_maintenance")
    private java.time.LocalDate nextMaintenance;

    @Column(name = "maintenance_days_left")
    private Integer maintenanceDaysLeft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Embedded
    private NotificationPreferences notificationPreferences;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabMetricSubscription> metricSubscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabMetric> metrics = new ArrayList<>();

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabAlert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabActivity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StorageUnit> storageUnits = new ArrayList<>();

    public Laboratory(com.test.backend.labs.domain.model.commands.CreateLaboratoryCommand command) {
        this.name = command.name();
        this.type = command.type();
        this.status = command.status();
        this.building = command.building();
        this.floor = command.floor();
        this.labCode = command.labCode();
        this.roomNumber = command.roomNumber();
        this.description = command.description();
        this.overallStatus = command.overallStatus();
        this.active = command.active();
        this.isLive = command.isLive();
        this.nextMaintenance = command.nextMaintenance();
        this.maintenanceDaysLeft = command.maintenanceDaysLeft();
        this.notificationPreferences = command.notificationPreferences();
        this.lastUpdate = java.time.LocalDateTime.now(java.time.ZoneOffset.UTC);
    }

    public Laboratory updateFrom(com.test.backend.labs.domain.model.commands.UpdateLaboratoryCommand command) {
        this.name = command.name();
        this.type = command.type();
        this.status = command.status();
        this.building = command.building();
        this.floor = command.floor();
        this.labCode = command.labCode();
        this.roomNumber = command.roomNumber();
        this.description = command.description();
        this.overallStatus = command.overallStatus();
        this.active = command.active();
        this.isLive = command.isLive();
        this.nextMaintenance = command.nextMaintenance();
        this.maintenanceDaysLeft = command.maintenanceDaysLeft();
        this.notificationPreferences = command.notificationPreferences();
        this.lastUpdate = java.time.LocalDateTime.now(java.time.ZoneOffset.UTC);
        return this;
    }

    public Integer getMaintenanceDaysLeft() {
        if (this.nextMaintenance == null) return null;
        return (int) java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), this.nextMaintenance);
    }
}
