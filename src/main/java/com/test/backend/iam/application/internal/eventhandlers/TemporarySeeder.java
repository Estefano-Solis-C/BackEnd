package com.test.backend.iam.application.internal.eventhandlers;

import com.test.backend.automation.domain.model.aggregates.UserProfile;
import com.test.backend.automation.domain.model.aggregates.GeneralSetting;
import com.test.backend.automation.domain.model.aggregates.SensorConfiguration;
import com.test.backend.automation.domain.model.aggregates.EquipmentThreshold;
import com.test.backend.automation.domain.model.entities.NotificationPreference;
import com.test.backend.automation.domain.model.entities.UserWorkspaceAccess;
import com.test.backend.automation.domain.model.entities.LabUserAccess;
import com.test.backend.automation.infrastructure.persistence.jpa.repositories.*;
import com.test.backend.labs.domain.model.aggregates.Laboratory;
import com.test.backend.labs.domain.model.entities.LabMetric;
import com.test.backend.labs.domain.model.entities.LabMetricSubscription;
import com.test.backend.labs.domain.model.valueobjets.NotificationPreferences;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.LaboratoryRepository;
import com.test.backend.telemetry.domain.model.aggregates.MetricType;
import com.test.backend.telemetry.domain.model.aggregates.SensorReading;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.MetricTypeRepository;
import com.test.backend.telemetry.infrastructure.persistence.jpa.repositories.SensorReadingRepository;
import com.test.backend.alerts.domain.model.aggregates.Alert;
import com.test.backend.alerts.domain.model.entities.AlertMetric;
import com.test.backend.alerts.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.test.backend.history.domain.model.aggregates.HistoryRecord;
import com.test.backend.history.infrastructure.persistence.jpa.repositories.HistoryRecordRepository;
import com.test.backend.compliance.domain.model.aggregates.ComplianceReport;
import com.test.backend.compliance.infrastructure.persistence.jpa.repositories.ComplianceReportRepository;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.queries.GetUserByEmailQuery;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.domain.services.UserQueryService;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.test.backend.labs.domain.model.aggregates.Workspace;
import com.test.backend.labs.infrastructure.persistence.jpa.repositories.WorkspaceRepository;
import jakarta.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Temporary Demo Seeder.
 * Populates the database with rich historical metrics, multiple labs, active alerts,
 * and configures a default Operator user (admin@safelab.com) with lab access.
 * Delete or comment out the @Component annotation to disable seeding.
 */
@Component
@Order(2)
public class TemporarySeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TemporarySeeder.class);

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RoleRepository automationRoleRepository;
    private final GeneralSettingRepository generalSettingRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final SensorConfigurationRepository sensorConfigurationRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final MetricTypeRepository metricTypeRepository;
    private final EquipmentThresholdRepository equipmentThresholdRepository;
    private final AlertRepository alertRepository;
    private final HistoryRecordRepository historyRecordRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserWorkspaceAccessRepository userWorkspaceAccessRepository;
    private final EntityManager entityManager;
    private final ComplianceReportRepository complianceReportRepository;

    public TemporarySeeder(UserCommandService userCommandService,
                           UserQueryService userQueryService,
                           UserRepository userRepository,
                           UserProfileRepository userProfileRepository,
                           RoleRepository automationRoleRepository,
                           GeneralSettingRepository generalSettingRepository,
                           NotificationPreferenceRepository notificationPreferenceRepository,
                           LaboratoryRepository laboratoryRepository,
                           SensorConfigurationRepository sensorConfigurationRepository,
                           SensorReadingRepository sensorReadingRepository,
                           MetricTypeRepository metricTypeRepository,
                           EquipmentThresholdRepository equipmentThresholdRepository,
                           AlertRepository alertRepository,
                           HistoryRecordRepository historyRecordRepository,
                           WorkspaceRepository workspaceRepository,
                           UserWorkspaceAccessRepository userWorkspaceAccessRepository,
                           EntityManager entityManager,
                           ComplianceReportRepository complianceReportRepository) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.automationRoleRepository = automationRoleRepository;
        this.generalSettingRepository = generalSettingRepository;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.sensorConfigurationRepository = sensorConfigurationRepository;
        this.sensorReadingRepository = sensorReadingRepository;
        this.metricTypeRepository = metricTypeRepository;
        this.equipmentThresholdRepository = equipmentThresholdRepository;
        this.alertRepository = alertRepository;
        this.historyRecordRepository = historyRecordRepository;
        this.workspaceRepository = workspaceRepository;
        this.userWorkspaceAccessRepository = userWorkspaceAccessRepository;
        this.entityManager = entityManager;
        this.complianceReportRepository = complianceReportRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Initializing Temporary Demo Data Seeder...");

        // 1. Seed Default Operator User (admin@safelab.com / admin123)
        String userEmail = "admin@safelab.com";
        var existingUser = userQueryService.handle(new GetUserByEmailQuery(userEmail));

        if (existingUser.isEmpty()) {
            var signUpCommand = new SignUpCommand(
                    userEmail,
                    "admin123",
                    "SafeLab Operator",
                    List.of(Roles.ROLE_USER) // Regular User role
            );
            userCommandService.handle(signUpCommand);
            logger.info("IAM User created: {} / admin123", userEmail);
        } else {
            logger.info("IAM User already exists. Skipping IAM registration.");
            // Ensure the default user is confirmed/verified
            var user = existingUser.get();
            if (!user.isConfirmed()) {
                user.setConfirmed(true);
                user.setConfirmationCode(null);
                userRepository.save(user);
                logger.info("IAM User admin@safelab.com existed but was not confirmed. Forced confirmation to true.");
            }
        }

        // 2. Fetch or Create Workspace
        var workspaceOpt = workspaceRepository.findAll().stream()
                .filter(w -> w.getName().contains("SafeLab") || w.getName().contains("Workspace"))
                .findFirst();

        Workspace tempWorkspace;
        if (workspaceOpt.isEmpty()) {
            var ws = new Workspace();
            ws.setName("SafeLab Base Workspace");
            ws.setCode("base-operator-workspace");
            tempWorkspace = workspaceRepository.save(ws);
            logger.info("Workspace created: {}", tempWorkspace.getName());
        } else {
            tempWorkspace = workspaceOpt.get();
        }
        final Workspace workspace = tempWorkspace;

        // 3. Fetch or Create Automation Role
        var operatorRole = automationRoleRepository.findByName("Operator")
                .orElseGet(() -> {
                    var role = new com.test.backend.automation.domain.model.entities.Role();
                    role.setName("Operator");
                    role.setDescription("Operator role");
                    return automationRoleRepository.save(role);
                });

        // 4. Bind Profile with Operator role and access tier
        UserProfile profile = userProfileRepository.findByEmail(userEmail)
                .orElseGet(() -> {
                    var p = new UserProfile();
                    p.setEmail(userEmail);
                    p.setWorkspaceId(workspace.getId());
                    return p;
                });

        profile.setRole(operatorRole);
        profile.setFullName("SafeLab Operator");
        profile.setProfessionalTitle("Lab Operator");
        profile.setEmployeeId("EMP-00001");
        profile.setSystemState("Active");
        profile.setAccessTier("Tier 2");
        profile.setDefaultStartShift("08:00 AM");
        profile.setShiftDuration("8 Hours");
        profile.setAutoGenerateShiftReport(false);
        profile.setWorkspaceId(workspace.getId());
        profile = userProfileRepository.save(profile);
        logger.info("UserProfile synchronized and set to Operator role.");

        // 5. Workspace Access Setup
        final UserProfile finalProfile = profile;
        final Workspace finalWorkspace = workspace;
        userWorkspaceAccessRepository.findByUserProfileEmailAndWorkspaceId(userEmail, workspace.getId())
                .orElseGet(() -> {
                    var access = new UserWorkspaceAccess();
                    access.setUserProfile(finalProfile);
                    access.setWorkspace(finalWorkspace);
                    access.setRole(operatorRole);
                    return userWorkspaceAccessRepository.save(access);
                });

        // 6. Seed General Settings
        if (generalSettingRepository.count() == 0) {
            generalSettingRepository.save(new GeneralSetting("tempWarningLevel", "2c", "alert_thresholds", "Deviation for warning visual state"));
            generalSettingRepository.save(new GeneralSetting("tempCriticalLevel", "5c", "alert_thresholds", "Deviation for critical push notification"));
            generalSettingRepository.save(new GeneralSetting("dailySummary", "false", "reports", "Daily snapshot summary report"));
            generalSettingRepository.save(new GeneralSetting("weeklyReport", "false", "reports", "Weekly trend and compliance report"));
            generalSettingRepository.save(new GeneralSetting("instantSensorAlerts", "true", "notifications", "Instant alert for connection loss"));
            generalSettingRepository.save(new GeneralSetting("coordinatorPhone", "+1 (555) 000-0000", "general", "Coordinator notification phone number"));
            generalSettingRepository.save(new GeneralSetting("quietHoursEnabled", "true", "general", "Silence alerts during quiet hours"));
            generalSettingRepository.save(new GeneralSetting("quietHoursStart", "10:00 PM", "general", "Start of quiet hours"));
            generalSettingRepository.save(new GeneralSetting("quietHoursEnd", "06:00 AM", "general", "End of quiet hours"));
            logger.info("General Settings seeded.");
        }

        // 7. Seed Notification Preferences
        if (notificationPreferenceRepository.findByUserProfileId(profile.getId()).isEmpty()) {
            notificationPreferenceRepository.save(new NotificationPreference(profile, "Email", true, "All", "All notifications via email"));
            notificationPreferenceRepository.save(new NotificationPreference(profile, "In-App", true, "All", "Realtime alerts in the application"));
            notificationPreferenceRepository.save(new NotificationPreference(profile, "SMS", true, "Critical Only", "Urgent alerts on mobile devices"));
            logger.info("Notification preferences seeded.");
        }

        // 8. Seed Laboratories, Metric Subscriptions, Equipment, Sensors, and Readings
        if (laboratoryRepository.findByWorkspaceId(workspace.getId()).isEmpty()) {
            logger.info("Seeding laboratories and sensor infrastructure...");

            var tempType = metricTypeRepository.findByKey("temperature").orElse(null);
            var humidityType = metricTypeRepository.findByKey("humidity").orElse(null);
            var co2Type = metricTypeRepository.findByKey("co2").orElse(null);
            var pressureType = metricTypeRepository.findByKey("pressure").orElse(null);
            var airQualityType = metricTypeRepository.findByKey("air_quality").orElse(null);
            var vibrationType = metricTypeRepository.findByKey("vibration").orElse(null);

            // Lab 1: Bio-Safety Lab 04
            var lab1 = new Laboratory();
            lab1.setName("Bio-Safety Lab 04");
            lab1.setType("Biohazard Level 3");
            lab1.setStatus("Operational");
            lab1.setBuilding("Building A");
            lab1.setFloor("Floor 2");
            lab1.setLabCode("LAB-BSL4-01");
            lab1.setRoomNumber("204");
            lab1.setDescription("High pathogen containment research unit");
            lab1.setOverallStatus("Operational");
            lab1.setActive(true);
            lab1.setLive(true);
            lab1.setNextMaintenance(LocalDate.now().plusDays(45));
            lab1.setMaintenanceDaysLeft(45);
            lab1.setNotificationPreferences(new NotificationPreferences(true, true, false, false));
            lab1.setWorkspace(workspace);
            lab1 = laboratoryRepository.save(lab1);

            if (tempType != null) lab1.getMetricSubscriptions().add(new LabMetricSubscription(lab1, tempType, 2.0, 8.0, true));
            if (humidityType != null) lab1.getMetricSubscriptions().add(new LabMetricSubscription(lab1, humidityType, 30.0, 60.0, true));
            if (airQualityType != null) lab1.getMetricSubscriptions().add(new LabMetricSubscription(lab1, airQualityType, 0.0, 50.0, true));
            lab1 = laboratoryRepository.save(lab1);

            // Lab 2: Chemical Synthesis Lab
            var lab2 = new Laboratory();
            lab2.setName("Chemical Synthesis Lab");
            lab2.setType("Chemical Synthesis");
            lab2.setStatus("Warning");
            lab2.setBuilding("Building B");
            lab2.setFloor("Floor 1");
            lab2.setLabCode("LAB-CHEM-02");
            lab2.setRoomNumber("102");
            lab2.setDescription("Organic compound development and analysis");
            lab2.setOverallStatus("Warning");
            lab2.setActive(true);
            lab2.setLive(true);
            lab2.setNextMaintenance(LocalDate.now().plusDays(15));
            lab2.setMaintenanceDaysLeft(15);
            lab2.setNotificationPreferences(new NotificationPreferences(true, false, true, false));
            lab2.setWorkspace(workspace);
            lab2 = laboratoryRepository.save(lab2);

            if (tempType != null) lab2.getMetricSubscriptions().add(new LabMetricSubscription(lab2, tempType, 15.0, 25.0, true));
            if (co2Type != null) lab2.getMetricSubscriptions().add(new LabMetricSubscription(lab2, co2Type, 400.0, 1000.0, true));
            if (humidityType != null) lab2.getMetricSubscriptions().add(new LabMetricSubscription(lab2, humidityType, 30.0, 65.0, true));
            lab2 = laboratoryRepository.save(lab2);

            // Lab 3: Cryogenic Storage Room
            var lab3 = new Laboratory();
            lab3.setName("Cryogenic Storage Room");
            lab3.setType("Cryo Storage");
            lab3.setStatus("Critical");
            lab3.setBuilding("Building A");
            lab3.setFloor("Floor 1");
            lab3.setLabCode("LAB-CRYO-03");
            lab3.setRoomNumber("105");
            lab3.setDescription("Ultra-low temperature biological sample repository");
            lab3.setOverallStatus("Critical");
            lab3.setActive(true);
            lab3.setLive(true);
            lab3.setNextMaintenance(LocalDate.now().plusDays(60));
            lab3.setMaintenanceDaysLeft(60);
            lab3.setNotificationPreferences(new NotificationPreferences(true, true, true, true));
            lab3.setWorkspace(workspace);
            lab3 = laboratoryRepository.save(lab3);

            if (tempType != null) lab3.getMetricSubscriptions().add(new LabMetricSubscription(lab3, tempType, -200.0, -180.0, true));
            if (pressureType != null) lab3.getMetricSubscriptions().add(new LabMetricSubscription(lab3, pressureType, 950.0, 1050.0, true));
            lab3 = laboratoryRepository.save(lab3);

            // Lab 4: Analytical Testing Unit
            var lab4 = new Laboratory();
            lab4.setName("Analytical Testing Unit");
            lab4.setType("Analytical Labs");
            lab4.setStatus("Operational");
            lab4.setBuilding("Building C");
            lab4.setFloor("Floor 3");
            lab4.setLabCode("LAB-ANAL-04");
            lab4.setRoomNumber("310");
            lab4.setDescription("Spectrometry and chromatography services");
            lab4.setOverallStatus("Operational");
            lab4.setActive(true);
            lab4.setLive(true);
            lab4.setNextMaintenance(LocalDate.now().plusDays(90));
            lab4.setMaintenanceDaysLeft(90);
            lab4.setNotificationPreferences(new NotificationPreferences(true, false, false, false));
            lab4.setWorkspace(workspace);
            lab4 = laboratoryRepository.save(lab4);

            if (tempType != null) lab4.getMetricSubscriptions().add(new LabMetricSubscription(lab4, tempType, 18.0, 24.0, true));
            if (humidityType != null) lab4.getMetricSubscriptions().add(new LabMetricSubscription(lab4, humidityType, 25.0, 55.0, true));
            lab4 = laboratoryRepository.save(lab4);

            // Lab 5: Molecular Biology Lab
            var lab5 = new Laboratory();
            lab5.setName("Molecular Biology Lab");
            lab5.setType("Molecular Biology");
            lab5.setStatus("Operational");
            lab5.setBuilding("Building A");
            lab5.setFloor("Floor 3");
            lab5.setLabCode("LAB-MOLB-05");
            lab5.setRoomNumber("302");
            lab5.setDescription("DNA/RNA extraction and amplification research");
            lab5.setOverallStatus("Operational");
            lab5.setActive(true);
            lab5.setLive(true);
            lab5.setNextMaintenance(LocalDate.now().plusDays(30));
            lab5.setMaintenanceDaysLeft(30);
            lab5.setNotificationPreferences(new NotificationPreferences(true, true, false, false));
            lab5.setWorkspace(workspace);
            lab5 = laboratoryRepository.save(lab5);

            if (tempType != null) lab5.getMetricSubscriptions().add(new LabMetricSubscription(lab5, tempType, 18.0, 26.0, true));
            if (humidityType != null) lab5.getMetricSubscriptions().add(new LabMetricSubscription(lab5, humidityType, 30.0, 60.0, true));
            if (airQualityType != null) lab5.getMetricSubscriptions().add(new LabMetricSubscription(lab5, airQualityType, 0.0, 50.0, true));
            lab5 = laboratoryRepository.save(lab5);

            // Lab 6: Clean Room Class 100
            var lab6 = new Laboratory();
            lab6.setName("Clean Room Class 100");
            lab6.setType("Micro-fabrication");
            lab6.setStatus("Operational");
            lab6.setBuilding("Building C");
            lab6.setFloor("Floor 1");
            lab6.setLabCode("LAB-CLEAN-06");
            lab6.setRoomNumber("112");
            lab6.setDescription("Particulate-free environment for sensitive material synthesis");
            lab6.setOverallStatus("Operational");
            lab6.setActive(true);
            lab6.setLive(true);
            lab6.setNextMaintenance(LocalDate.now().plusDays(10));
            lab6.setMaintenanceDaysLeft(10);
            lab6.setNotificationPreferences(new NotificationPreferences(true, true, true, false));
            lab6.setWorkspace(workspace);
            lab6 = laboratoryRepository.save(lab6);

            if (tempType != null) lab6.getMetricSubscriptions().add(new LabMetricSubscription(lab6, tempType, 19.0, 23.0, true));
            if (humidityType != null) lab6.getMetricSubscriptions().add(new LabMetricSubscription(lab6, humidityType, 40.0, 50.0, true));
            if (pressureType != null) lab6.getMetricSubscriptions().add(new LabMetricSubscription(lab6, pressureType, 50.0, 150.0, true));
            if (airQualityType != null) lab6.getMetricSubscriptions().add(new LabMetricSubscription(lab6, airQualityType, 0.0, 50.0, true));
            lab6 = laboratoryRepository.save(lab6);

            // Lab 7: Radiation Research Facility
            var lab7 = new Laboratory();
            lab7.setName("Radiation Research Facility");
            lab7.setType("Nuclear/Radiology");
            lab7.setStatus("Warning");
            lab7.setBuilding("Building D");
            lab7.setFloor("Floor B1");
            lab7.setLabCode("LAB-RAD-07");
            lab7.setRoomNumber("015");
            lab7.setDescription("Shielded laboratory for isotope tracking and radiation physics");
            lab7.setOverallStatus("Warning");
            lab7.setActive(true);
            lab7.setLive(true);
            lab7.setNextMaintenance(LocalDate.now().plusDays(25));
            lab7.setMaintenanceDaysLeft(25);
            lab7.setNotificationPreferences(new NotificationPreferences(true, false, true, true));
            lab7.setWorkspace(workspace);
            lab7 = laboratoryRepository.save(lab7);

            if (tempType != null) lab7.getMetricSubscriptions().add(new LabMetricSubscription(lab7, tempType, 18.0, 24.0, true));
            if (pressureType != null) lab7.getMetricSubscriptions().add(new LabMetricSubscription(lab7, pressureType, 950.0, 1050.0, true));
            if (vibrationType != null) lab7.getMetricSubscriptions().add(new LabMetricSubscription(lab7, vibrationType, 0.0, 3.0, true));
            lab7 = laboratoryRepository.save(lab7);

            // Lab 8: Pharmaceutical Formulation Unit
            var lab8 = new Laboratory();
            lab8.setName("Pharmaceutical Formulation Unit");
            lab8.setType("Pharmaceutics");
            lab8.setStatus("Critical");
            lab8.setBuilding("Building B");
            lab8.setFloor("Floor 2");
            lab8.setLabCode("LAB-PHARM-08");
            lab8.setRoomNumber("205");
            lab8.setDescription("Solid-dosage form blending, granulation and compression");
            lab8.setOverallStatus("Critical");
            lab8.setActive(true);
            lab8.setLive(true);
            lab8.setNextMaintenance(LocalDate.now().plusDays(5));
            lab8.setMaintenanceDaysLeft(5);
            lab8.setNotificationPreferences(new NotificationPreferences(true, true, true, true));
            lab8.setWorkspace(workspace);
            lab8 = laboratoryRepository.save(lab8);

            if (tempType != null) lab8.getMetricSubscriptions().add(new LabMetricSubscription(lab8, tempType, 15.0, 25.0, true));
            if (humidityType != null) lab8.getMetricSubscriptions().add(new LabMetricSubscription(lab8, humidityType, 30.0, 50.0, true));
            if (co2Type != null) lab8.getMetricSubscriptions().add(new LabMetricSubscription(lab8, co2Type, 400.0, 1000.0, true));
            lab8 = laboratoryRepository.save(lab8);

            // Lab 9: Genetics Sequencing Center
            var lab9 = new Laboratory();
            lab9.setName("Genetics Sequencing Center");
            lab9.setType("Genomics");
            lab9.setStatus("Warning");
            lab9.setBuilding("Building A");
            lab9.setFloor("Floor 2");
            lab9.setLabCode("LAB-GENE-09");
            lab9.setRoomNumber("218");
            lab9.setDescription("High-throughput next generation sequencing services");
            lab9.setOverallStatus("Warning");
            lab9.setActive(true);
            lab9.setLive(true);
            lab9.setNextMaintenance(LocalDate.now().plusDays(40));
            lab9.setMaintenanceDaysLeft(40);
            lab9.setNotificationPreferences(new NotificationPreferences(true, true, false, false));
            lab9.setWorkspace(workspace);
            lab9 = laboratoryRepository.save(lab9);

            if (tempType != null) lab9.getMetricSubscriptions().add(new LabMetricSubscription(lab9, tempType, 18.0, 25.0, true));
            if (humidityType != null) lab9.getMetricSubscriptions().add(new LabMetricSubscription(lab9, humidityType, 30.0, 60.0, true));
            lab9 = laboratoryRepository.save(lab9);

            // Lab 10: Microbiology Incubator Zone
            var lab10 = new Laboratory();
            lab10.setName("Microbiology Incubator Zone");
            lab10.setType("Microbiology");
            lab10.setStatus("Warning");
            lab10.setBuilding("Building B");
            lab10.setFloor("Floor 3");
            lab10.setLabCode("LAB-MICRO-10");
            lab10.setRoomNumber("304");
            lab10.setDescription("Controlled culture growing environmental chambers");
            lab10.setOverallStatus("Warning");
            lab10.setActive(true);
            lab10.setLive(true);
            lab10.setNextMaintenance(LocalDate.now().plusDays(50));
            lab10.setMaintenanceDaysLeft(50);
            lab10.setNotificationPreferences(new NotificationPreferences(true, false, true, false));
            lab10.setWorkspace(workspace);
            lab10 = laboratoryRepository.save(lab10);

            if (tempType != null) lab10.getMetricSubscriptions().add(new LabMetricSubscription(lab10, tempType, 35.0, 37.5, true));
            if (humidityType != null) lab10.getMetricSubscriptions().add(new LabMetricSubscription(lab10, humidityType, 80.0, 95.0, true));
            if (co2Type != null) lab10.getMetricSubscriptions().add(new LabMetricSubscription(lab10, co2Type, 4.0, 6.0, true));
            lab10 = laboratoryRepository.save(lab10);

            // Lab 11: Toxicology Isolation Suite
            var lab11 = new Laboratory();
            lab11.setName("Toxicology Isolation Suite");
            lab11.setType("Toxicology");
            lab11.setStatus("Critical");
            lab11.setBuilding("Building D");
            lab11.setFloor("Floor 2");
            lab11.setLabCode("LAB-TOX-11");
            lab11.setRoomNumber("220");
            lab11.setDescription("Handling high-potency API and containment materials");
            lab11.setOverallStatus("Critical");
            lab11.setActive(true);
            lab11.setLive(true);
            lab11.setNextMaintenance(LocalDate.now().plusDays(12));
            lab11.setMaintenanceDaysLeft(12);
            lab11.setNotificationPreferences(new NotificationPreferences(true, true, true, true));
            lab11.setWorkspace(workspace);
            lab11 = laboratoryRepository.save(lab11);

            if (tempType != null) lab11.getMetricSubscriptions().add(new LabMetricSubscription(lab11, tempType, 18.0, 22.0, true));
            if (humidityType != null) lab11.getMetricSubscriptions().add(new LabMetricSubscription(lab11, humidityType, 30.0, 50.0, true));
            if (pressureType != null) lab11.getMetricSubscriptions().add(new LabMetricSubscription(lab11, pressureType, 900.0, 1020.0, true));
            if (airQualityType != null) lab11.getMetricSubscriptions().add(new LabMetricSubscription(lab11, airQualityType, 0.0, 50.0, true));
            lab11 = laboratoryRepository.save(lab11);

            // Lab 12: Laser Optics & Physics Lab
            var lab12 = new Laboratory();
            lab12.setName("Laser Optics & Physics Lab");
            lab12.setType("Physics Labs");
            lab12.setStatus("Operational");
            lab12.setBuilding("Building C");
            lab12.setFloor("Floor 2");
            lab12.setLabCode("LAB-PHYS-12");
            lab12.setRoomNumber("210");
            lab12.setDescription("Ultra-precise vibration isolated optical platforms");
            lab12.setOverallStatus("Operational");
            lab12.setActive(true);
            lab12.setLive(true);
            lab12.setNextMaintenance(LocalDate.now().plusDays(75));
            lab12.setMaintenanceDaysLeft(75);
            lab12.setNotificationPreferences(new NotificationPreferences(true, true, false, false));
            lab12.setWorkspace(workspace);
            lab12 = laboratoryRepository.save(lab12);

            if (tempType != null) lab12.getMetricSubscriptions().add(new LabMetricSubscription(lab12, tempType, 19.0, 22.0, true));
            if (vibrationType != null) lab12.getMetricSubscriptions().add(new LabMetricSubscription(lab12, vibrationType, 0.0, 3.0, true));
            if (airQualityType != null) lab12.getMetricSubscriptions().add(new LabMetricSubscription(lab12, airQualityType, 0.0, 50.0, true));
            lab12 = laboratoryRepository.save(lab12);

            // 9. Grant profile explicit access to all laboratories
            profile.getLabAccesses().clear();
            profile = userProfileRepository.save(profile);

            List<Laboratory> allLabs = List.of(lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, lab9, lab10, lab11, lab12);
            for (var lab : allLabs) {
                var labAccess = new LabUserAccess();
                labAccess.setUserProfile(profile);
                labAccess.setLaboratory(lab);
                profile.getLabAccesses().add(labAccess);
            }
            profile = userProfileRepository.save(profile);
            logger.info("Assigned LabUserAccess to profile for all 12 laboratories.");

            // 10. Seed Equipment Thresholds
            var eq1 = equipmentThresholdRepository.save(new EquipmentThreshold(lab1, "ULT Freezer F-07", "biotech", -86.0, -75.0, -78.0, "°C", -80.2, "normal"));
            var eq2 = equipmentThresholdRepository.save(new EquipmentThreshold(lab1, "Refrigerator B2", "biotech", 2.0, 8.0, 7.0, "°C", 8.5, "critical")); // breach!
            var eq3 = equipmentThresholdRepository.save(new EquipmentThreshold(lab2, "CO2 Incubator CM-01", "biotech", 400.0, 1200.0, 1000.0, "ppm", 1050.0, "warning")); // warning!
            var eq4 = equipmentThresholdRepository.save(new EquipmentThreshold(lab2, "Reactor R-02", "industrial", 0.0, 3.0, 2.0, "mm/s", 3.5, "warning")); // vibration warning!
            var eq5 = equipmentThresholdRepository.save(new EquipmentThreshold(lab3, "LN2 Tank Cryo-01", "biotech", -200.0, -180.0, -185.0, "°C", -170.0, "critical")); // critical breach!
            var eq6 = equipmentThresholdRepository.save(new EquipmentThreshold(lab4, "GC-MS Column Oven", "analytical", 30.0, 350.0, 300.0, "°C", 150.0, "normal"));
            var eq7 = equipmentThresholdRepository.save(new EquipmentThreshold(lab5, "PCR Thermocycler T-12", "biotech", 4.0, 98.0, 95.0, "°C", 94.8, "normal"));
            var eq8 = equipmentThresholdRepository.save(new EquipmentThreshold(lab6, "HEPA Filtration Unit H-1", "industrial", 50.0, 150.0, 120.0, "Pa", 45.0, "critical")); // breach!
            var eq9 = equipmentThresholdRepository.save(new EquipmentThreshold(lab7, "Gamma Counter GC-03", "analytical", 0.0, 100.0, 80.0, "cpm", 12.0, "normal"));
            var eq10 = equipmentThresholdRepository.save(new EquipmentThreshold(lab8, "Granulator G-08", "industrial", 10.0, 50.0, 40.0, "rpm", 43.0, "warning")); // warning!
            var eq11 = equipmentThresholdRepository.save(new EquipmentThreshold(lab9, "Sequencer Seq-02", "biotech", 18.0, 25.0, 22.0, "°C", 26.5, "warning")); // breach/warning!
            var eq12 = equipmentThresholdRepository.save(new EquipmentThreshold(lab10, "Anaerobic Chamber AC-1", "biotech", 0.0, 5.0, 2.0, "%O2", 4.8, "warning")); // warning!
            var eq13 = equipmentThresholdRepository.save(new EquipmentThreshold(lab11, "Fume Hood F-09", "industrial", 0.3, 0.8, 0.5, "m/s", 0.2, "critical")); // breach!
            var eq14 = equipmentThresholdRepository.save(new EquipmentThreshold(lab12, "Optical Table OT-04", "industrial", 0.0, 3.0, 2.0, "mm/s", 3.2, "warning")); // warning!
            logger.info("Equipment thresholds seeded.");

            // 11. Seed Sensors
            var sensor1 = new SensorConfiguration();
            sensor1.setSensorName("Sensor T-01");
            sensor1.setType("temperature");
            sensor1.setUnit("MAC-T01-REF");
            sensor1.setActive(true);
            sensor1.setStatus("ONLINE");
            sensor1.setCalibrationDate(LocalDate.now().minusMonths(3));
            sensor1.setLastConnected(LocalDateTime.now().minusMinutes(2));
            sensor1.setLaboratory(lab1);
            sensor1.setEquipment(eq2);
            sensor1 = sensorConfigurationRepository.save(sensor1);

            var sensor2 = new SensorConfiguration();
            sensor2.setSensorName("Sensor H-02");
            sensor2.setType("humidity");
            sensor2.setUnit("MAC-H02-AMB");
            sensor2.setActive(true);
            sensor2.setStatus("ONLINE");
            sensor2.setCalibrationDate(LocalDate.now().minusMonths(4));
            sensor2.setLastConnected(LocalDateTime.now().minusMinutes(5));
            sensor2.setLaboratory(lab1);
            sensor2.setEquipment(null);
            sensor2 = sensorConfigurationRepository.save(sensor2);

            var sensor3 = new SensorConfiguration();
            sensor3.setSensorName("Sensor C-03");
            sensor3.setType("co2");
            sensor3.setUnit("MAC-C03-INC");
            sensor3.setActive(true);
            sensor3.setStatus("ONLINE");
            sensor3.setCalibrationDate(LocalDate.now().minusMonths(1));
            sensor3.setLastConnected(LocalDateTime.now().minusMinutes(1));
            sensor3.setLaboratory(lab2);
            sensor3.setEquipment(eq3);
            sensor3 = sensorConfigurationRepository.save(sensor3);

            var sensor4 = new SensorConfiguration();
            sensor4.setSensorName("Sensor T-04");
            sensor4.setType("temperature");
            sensor4.setUnit("MAC-T04-CRYO");
            sensor4.setActive(true);
            sensor4.setStatus("ONLINE");
            sensor4.setCalibrationDate(LocalDate.now().minusMonths(2));
            sensor4.setLastConnected(LocalDateTime.now().minusMinutes(3));
            sensor4.setLaboratory(lab3);
            sensor4.setEquipment(eq5);
            sensor4 = sensorConfigurationRepository.save(sensor4);

            var sensor5 = new SensorConfiguration();
            sensor5.setSensorName("Sensor P-05");
            sensor5.setType("pressure");
            sensor5.setUnit("MAC-P05-PRES");
            sensor5.setActive(true);
            sensor5.setStatus("ONLINE");
            sensor5.setCalibrationDate(LocalDate.now().minusMonths(6));
            sensor5.setLastConnected(LocalDateTime.now().minusMinutes(10));
            sensor5.setLaboratory(lab3);
            sensor5.setEquipment(null);
            sensor5 = sensorConfigurationRepository.save(sensor5);

            var sensor6 = new SensorConfiguration();
            sensor6.setSensorName("Sensor A-07");
            sensor6.setType("air_quality");
            sensor6.setUnit("MAC-A07-AIR");
            sensor6.setActive(true);
            sensor6.setStatus("ONLINE");
            sensor6.setCalibrationDate(LocalDate.now().minusMonths(5));
            sensor6.setLastConnected(LocalDateTime.now().minusMinutes(7));
            sensor6.setLaboratory(lab2);
            sensor6.setEquipment(null);
            sensor6 = sensorConfigurationRepository.save(sensor6);

            var sensor7 = new SensorConfiguration();
            sensor7.setSensorName("Sensor V-08");
            sensor7.setType("vibration");
            sensor7.setUnit("MAC-V08-VIB");
            sensor7.setActive(true);
            sensor7.setStatus("ONLINE");
            sensor7.setCalibrationDate(LocalDate.now().minusMonths(1));
            sensor7.setLastConnected(LocalDateTime.now().minusMinutes(4));
            sensor7.setLaboratory(lab2);
            sensor7.setEquipment(eq4);
            sensor7 = sensorConfigurationRepository.save(sensor7);

            var sensor8 = new SensorConfiguration();
            sensor8.setSensorName("Sensor T-06");
            sensor8.setType("temperature");
            sensor8.setUnit("MAC-T06-OFFLINE");
            sensor8.setActive(true);
            sensor8.setStatus("OFFLINE");
            sensor8.setCalibrationDate(LocalDate.now().minusMonths(8));
            sensor8.setLastConnected(LocalDateTime.now().minusDays(2));
            sensor8.setLaboratory(lab4);
            sensor8.setEquipment(eq6);
            sensor8 = sensorConfigurationRepository.save(sensor8);

            var sensor9 = new SensorConfiguration();
            sensor9.setSensorName("Sensor T-09");
            sensor9.setType("temperature");
            sensor9.setUnit("MAC-T09-MOLB");
            sensor9.setActive(true);
            sensor9.setStatus("ONLINE");
            sensor9.setCalibrationDate(LocalDate.now().minusMonths(2));
            sensor9.setLastConnected(LocalDateTime.now().minusMinutes(3));
            sensor9.setLaboratory(lab5);
            sensor9.setEquipment(eq7);
            sensor9 = sensorConfigurationRepository.save(sensor9);

            var sensor10 = new SensorConfiguration();
            sensor10.setSensorName("Sensor H-10");
            sensor10.setType("humidity");
            sensor10.setUnit("MAC-H10-MOLB");
            sensor10.setActive(true);
            sensor10.setStatus("ONLINE");
            sensor10.setCalibrationDate(LocalDate.now().minusMonths(3));
            sensor10.setLastConnected(LocalDateTime.now().minusMinutes(5));
            sensor10.setLaboratory(lab5);
            sensor10.setEquipment(null);
            sensor10 = sensorConfigurationRepository.save(sensor10);

            var sensor11 = new SensorConfiguration();
            sensor11.setSensorName("Sensor P-11");
            sensor11.setType("pressure");
            sensor11.setUnit("MAC-P11-CLEAN");
            sensor11.setActive(true);
            sensor11.setStatus("ONLINE");
            sensor11.setCalibrationDate(LocalDate.now().minusMonths(1));
            sensor11.setLastConnected(LocalDateTime.now().minusMinutes(1));
            sensor11.setLaboratory(lab6);
            sensor11.setEquipment(eq8);
            sensor11 = sensorConfigurationRepository.save(sensor11);

            var sensor12 = new SensorConfiguration();
            sensor12.setSensorName("Sensor A-12");
            sensor12.setType("air_quality");
            sensor12.setUnit("MAC-A12-CLEAN");
            sensor12.setActive(true);
            sensor12.setStatus("ONLINE");
            sensor12.setCalibrationDate(LocalDate.now().minusMonths(4));
            sensor12.setLastConnected(LocalDateTime.now().minusMinutes(2));
            sensor12.setLaboratory(lab6);
            sensor12.setEquipment(null);
            sensor12 = sensorConfigurationRepository.save(sensor12);

            var sensor13 = new SensorConfiguration();
            sensor13.setSensorName("Sensor T-13");
            sensor13.setType("temperature");
            sensor13.setUnit("MAC-T13-RAD");
            sensor13.setActive(true);
            sensor13.setStatus("ONLINE");
            sensor13.setCalibrationDate(LocalDate.now().minusMonths(5));
            sensor13.setLastConnected(LocalDateTime.now().minusMinutes(6));
            sensor13.setLaboratory(lab7);
            sensor13.setEquipment(eq9);
            sensor13 = sensorConfigurationRepository.save(sensor13);

            var sensor14 = new SensorConfiguration();
            sensor14.setSensorName("Sensor P-14");
            sensor14.setType("pressure");
            sensor14.setUnit("MAC-P14-RAD");
            sensor14.setActive(true);
            sensor14.setStatus("ONLINE");
            sensor14.setCalibrationDate(LocalDate.now().minusMonths(2));
            sensor14.setLastConnected(LocalDateTime.now().minusMinutes(4));
            sensor14.setLaboratory(lab7);
            sensor14.setEquipment(null);
            sensor14 = sensorConfigurationRepository.save(sensor14);

            var sensor15 = new SensorConfiguration();
            sensor15.setSensorName("Sensor T-15");
            sensor15.setType("temperature");
            sensor15.setUnit("MAC-T15-PHARM");
            sensor15.setActive(true);
            sensor15.setStatus("ONLINE");
            sensor15.setCalibrationDate(LocalDate.now().minusMonths(3));
            sensor15.setLastConnected(LocalDateTime.now().minusMinutes(2));
            sensor15.setLaboratory(lab8);
            sensor15.setEquipment(null);
            sensor15 = sensorConfigurationRepository.save(sensor15);

            var sensor16 = new SensorConfiguration();
            sensor16.setSensorName("Sensor C-16");
            sensor16.setType("co2");
            sensor16.setUnit("MAC-C16-PHARM");
            sensor16.setActive(true);
            sensor16.setStatus("ONLINE");
            sensor16.setCalibrationDate(LocalDate.now().minusMonths(4));
            sensor16.setLastConnected(LocalDateTime.now().minusMinutes(3));
            sensor16.setLaboratory(lab8);
            sensor16.setEquipment(eq10);
            sensor16 = sensorConfigurationRepository.save(sensor16);

            var sensor17 = new SensorConfiguration();
            sensor17.setSensorName("Sensor T-17");
            sensor17.setType("temperature");
            sensor17.setUnit("MAC-T17-GENE");
            sensor17.setActive(true);
            sensor17.setStatus("ONLINE");
            sensor17.setCalibrationDate(LocalDate.now().minusMonths(1));
            sensor17.setLastConnected(LocalDateTime.now().minusMinutes(1));
            sensor17.setLaboratory(lab9);
            sensor17.setEquipment(eq11);
            sensor17 = sensorConfigurationRepository.save(sensor17);

            var sensor18 = new SensorConfiguration();
            sensor18.setSensorName("Sensor T-18");
            sensor18.setType("temperature");
            sensor18.setUnit("MAC-T18-MICRO");
            sensor18.setActive(true);
            sensor18.setStatus("ONLINE");
            sensor18.setCalibrationDate(LocalDate.now().minusMonths(2));
            sensor18.setLastConnected(LocalDateTime.now().minusMinutes(2));
            sensor18.setLaboratory(lab10);
            sensor18.setEquipment(eq12);
            sensor18 = sensorConfigurationRepository.save(sensor18);

            var sensor19 = new SensorConfiguration();
            sensor19.setSensorName("Sensor A-19");
            sensor19.setType("air_quality");
            sensor19.setUnit("MAC-A19-TOX");
            sensor19.setActive(true);
            sensor19.setStatus("ONLINE");
            sensor19.setCalibrationDate(LocalDate.now().minusMonths(3));
            sensor19.setLastConnected(LocalDateTime.now().minusMinutes(1));
            sensor19.setLaboratory(lab11);
            sensor19.setEquipment(eq13);
            sensor19 = sensorConfigurationRepository.save(sensor19);

            var sensor20 = new SensorConfiguration();
            sensor20.setSensorName("Sensor P-20");
            sensor20.setType("pressure");
            sensor20.setUnit("MAC-P20-TOX");
            sensor20.setActive(true);
            sensor20.setStatus("ONLINE");
            sensor20.setCalibrationDate(LocalDate.now().minusMonths(1));
            sensor20.setLastConnected(LocalDateTime.now().minusMinutes(2));
            sensor20.setLaboratory(lab11);
            sensor20.setEquipment(null);
            sensor20 = sensorConfigurationRepository.save(sensor20);

            var sensor21 = new SensorConfiguration();
            sensor21.setSensorName("Sensor V-21");
            sensor21.setType("vibration");
            sensor21.setUnit("MAC-V21-PHYS");
            sensor21.setActive(true);
            sensor21.setStatus("ONLINE");
            sensor21.setCalibrationDate(LocalDate.now().minusMonths(2));
            sensor21.setLastConnected(LocalDateTime.now().minusMinutes(4));
            sensor21.setLaboratory(lab12);
            sensor21.setEquipment(eq14);
            sensor21 = sensorConfigurationRepository.save(sensor21);

            var sensor22 = new SensorConfiguration();
            sensor22.setSensorName("Sensor T-22");
            sensor22.setType("temperature");
            sensor22.setUnit("MAC-T22-PHYS");
            sensor22.setActive(true);
            sensor22.setStatus("ONLINE");
            sensor22.setCalibrationDate(LocalDate.now().minusMonths(6));
            sensor22.setLastConnected(LocalDateTime.now().minusMinutes(10));
            sensor22.setLaboratory(lab12);
            sensor22.setEquipment(null);
            sensor22 = sensorConfigurationRepository.save(sensor22);

            logger.info("Sensors configured and seeded.");

            // 11.5. Seed Real-time Lab Metrics (required for frontend dashboard and detail view)
            logger.info("Seeding real-time laboratory metrics...");

            // Lab 1 Metrics
            var lm1 = new LabMetric();
            lm1.setLaboratory(lab1);
            lm1.setName("temperature");
            lm1.setValue("8.50");
            lm1.setUnit("°C");
            lm1.setStatus("CRITICAL");
            lm1.setIcon("device_thermostat");
            lm1.setSparkline("4.8,4.9,5.0,5.2,5.5,5.8,7.0,8.5");
            lm1.setThreshold(8.0);
            lm1.setObjectType("Refrigerator B2");

            var lm2 = new LabMetric();
            lm2.setLaboratory(lab1);
            lm2.setName("humidity");
            lm2.setValue("45.20");
            lm2.setUnit("%");
            lm2.setStatus("NORMAL");
            lm2.setIcon("water_drop");
            lm2.setSparkline("43.5,44.0,44.5,45.0,45.2");
            lm2.setThreshold(null);
            lm2.setObjectType("Ambient");

            var lm3 = new LabMetric();
            lm3.setLaboratory(lab1);
            lm3.setName("air_quality");
            lm3.setValue("22.00");
            lm3.setUnit("AQI");
            lm3.setStatus("NORMAL");
            lm3.setIcon("air");
            lm3.setSparkline("20.0,21.0,22.0");
            lm3.setThreshold(null);
            lm3.setObjectType("Ambient");

            lab1.getMetrics().addAll(List.of(lm1, lm2, lm3));
            laboratoryRepository.save(lab1);

            // Lab 2 Metrics
            var lm4 = new LabMetric();
            lm4.setLaboratory(lab2);
            lm4.setName("temperature");
            lm4.setValue("21.30");
            lm4.setUnit("°C");
            lm4.setStatus("NORMAL");
            lm4.setIcon("device_thermostat");
            lm4.setSparkline("20.5,21.0,21.3");
            lm4.setThreshold(null);
            lm4.setObjectType("Ambient");

            var lm5 = new LabMetric();
            lm5.setLaboratory(lab2);
            lm5.setName("co2");
            lm5.setValue("1050.00");
            lm5.setUnit("ppm");
            lm5.setStatus("WARNING");
            lm5.setIcon("co2");
            lm5.setSparkline("550.0,600.0,750.0,900.0,1050.0");
            lm5.setThreshold(1000.0);
            lm5.setObjectType("CO2 Incubator CM-01");

            var lm6 = new LabMetric();
            lm6.setLaboratory(lab2);
            lm6.setName("vibration");
            lm6.setValue("3.50");
            lm6.setUnit("mm/s");
            lm6.setStatus("WARNING");
            lm6.setIcon("graphic_eq");
            lm6.setSparkline("1.1,1.2,1.3,3.5");
            lm6.setThreshold(3.0);
            lm6.setObjectType("Reactor R-02");

            var lm7 = new LabMetric();
            lm7.setLaboratory(lab2);
            lm7.setName("humidity");
            lm7.setValue("48.50");
            lm7.setUnit("%");
            lm7.setStatus("NORMAL");
            lm7.setIcon("water_drop");
            lm7.setSparkline("46.0,47.2,48.5");
            lm7.setThreshold(null);
            lm7.setObjectType("Ambient");

            lab2.getMetrics().addAll(List.of(lm4, lm5, lm6, lm7));
            laboratoryRepository.save(lab2);

            // Lab 3 Metrics
            var lm8 = new LabMetric();
            lm8.setLaboratory(lab3);
            lm8.setName("temperature");
            lm8.setValue("-170.00");
            lm8.setUnit("°C");
            lm8.setStatus("CRITICAL");
            lm8.setIcon("device_thermostat");
            lm8.setSparkline("-192.0,-190.0,-185.0,-180.0,-170.0");
            lm8.setThreshold(-180.0);
            lm8.setObjectType("LN2 Tank Cryo-01");

            var lm9 = new LabMetric();
            lm9.setLaboratory(lab3);
            lm9.setName("pressure");
            lm9.setValue("1012.00");
            lm9.setUnit("hPa");
            lm9.setStatus("NORMAL");
            lm9.setIcon("compress");
            lm9.setSparkline("1008.0,1010.0,1012.0");
            lm9.setThreshold(null);
            lm9.setObjectType("Ambient");

            lab3.getMetrics().addAll(List.of(lm8, lm9));
            laboratoryRepository.save(lab3);

            // Lab 4 Metrics
            var lm10 = new LabMetric();
            lm10.setLaboratory(lab4);
            lm10.setName("temperature");
            lm10.setValue("21.80");
            lm10.setUnit("°C");
            lm10.setStatus("NORMAL");
            lm10.setIcon("device_thermostat");
            lm10.setSparkline("21.5,21.7,21.8");
            lm10.setThreshold(null);
            lm10.setObjectType("GC-MS Column Oven");

            var lm11 = new LabMetric();
            lm11.setLaboratory(lab4);
            lm11.setName("humidity");
            lm11.setValue("40.20");
            lm11.setUnit("%");
            lm11.setStatus("NORMAL");
            lm11.setIcon("water_drop");
            lm11.setSparkline("39.5,40.0,40.2");
            lm11.setThreshold(null);
            lm11.setObjectType("Ambient");

            lab4.getMetrics().addAll(List.of(lm10, lm11));
            laboratoryRepository.save(lab4);

            // Lab 5 Metrics
            var lm12 = new LabMetric();
            lm12.setLaboratory(lab5);
            lm12.setName("temperature");
            lm12.setValue("22.10");
            lm12.setUnit("°C");
            lm12.setStatus("NORMAL");
            lm12.setIcon("device_thermostat");
            lm12.setSparkline("21.8,22.0,22.1");
            lm12.setThreshold(null);
            lm12.setObjectType("Ambient");

            var lm13 = new LabMetric();
            lm13.setLaboratory(lab5);
            lm13.setName("humidity");
            lm13.setValue("45.00");
            lm13.setUnit("%");
            lm13.setStatus("NORMAL");
            lm13.setIcon("water_drop");
            lm13.setSparkline("44.5,45.0,45.2");
            lm13.setThreshold(null);
            lm13.setObjectType("Ambient");

            lab5.getMetrics().addAll(List.of(lm12, lm13));
            laboratoryRepository.save(lab5);

            // Lab 6 Metrics
            var lm14 = new LabMetric();
            lm14.setLaboratory(lab6);
            lm14.setName("pressure");
            lm14.setValue("45.00");
            lm14.setUnit("Pa");
            lm14.setStatus("CRITICAL");
            lm14.setIcon("compress");
            lm14.setSparkline("110.0,90.0,70.0,45.0");
            lm14.setThreshold(50.0);
            lm14.setObjectType("HEPA Filtration Unit H-1");

            var lm15 = new LabMetric();
            lm15.setLaboratory(lab6);
            lm15.setName("air_quality");
            lm15.setValue("75.00");
            lm15.setUnit("AQI");
            lm15.setStatus("CRITICAL");
            lm15.setIcon("air");
            lm15.setSparkline("15.0,30.0,55.0,75.0");
            lm15.setThreshold(50.0);
            lm15.setObjectType("Ambient");

            lab6.getMetrics().addAll(List.of(lm14, lm15));
            laboratoryRepository.save(lab6);

            // Lab 7 Metrics
            var lm16 = new LabMetric();
            lm16.setLaboratory(lab7);
            lm16.setName("temperature");
            lm16.setValue("20.00");
            lm16.setUnit("°C");
            lm16.setStatus("NORMAL");
            lm16.setIcon("device_thermostat");
            lm16.setSparkline("19.8,20.1,20.0");
            lm16.setThreshold(null);
            lm16.setObjectType("Ambient");

            var lm17 = new LabMetric();
            lm17.setLaboratory(lab7);
            lm17.setName("pressure");
            lm17.setValue("940.00");
            lm17.setUnit("hPa");
            lm17.setStatus("WARNING");
            lm17.setIcon("compress");
            lm17.setSparkline("960.0,950.0,940.0");
            lm17.setThreshold(950.0);
            lm17.setObjectType("Ambient");

            lab7.getMetrics().addAll(List.of(lm16, lm17));
            laboratoryRepository.save(lab7);

            // Lab 8 Metrics
            var lm18 = new LabMetric();
            lm18.setLaboratory(lab8);
            lm18.setName("temperature");
            lm18.setValue("28.50");
            lm18.setUnit("°C");
            lm18.setStatus("CRITICAL");
            lm18.setIcon("device_thermostat");
            lm18.setSparkline("22.0,24.0,26.5,28.5");
            lm18.setThreshold(25.0);
            lm18.setObjectType("Ambient");

            var lm19 = new LabMetric();
            lm19.setLaboratory(lab8);
            lm19.setName("co2");
            lm19.setValue("1100.00");
            lm19.setUnit("ppm");
            lm19.setStatus("WARNING");
            lm19.setIcon("co2");
            lm19.setSparkline("800.0,950.0,1100.0");
            lm19.setThreshold(1000.0);
            lm19.setObjectType("Granulator G-08");

            lab8.getMetrics().addAll(List.of(lm18, lm19));
            laboratoryRepository.save(lab8);

            // Lab 9 Metrics
            var lm20 = new LabMetric();
            lm20.setLaboratory(lab9);
            lm20.setName("temperature");
            lm20.setValue("26.50");
            lm20.setUnit("°C");
            lm20.setStatus("WARNING");
            lm20.setIcon("device_thermostat");
            lm20.setSparkline("22.0,23.5,25.0,26.5");
            lm20.setThreshold(25.0);
            lm20.setObjectType("Sequencer Seq-02");

            var lm21 = new LabMetric();
            lm21.setLaboratory(lab9);
            lm21.setName("humidity");
            lm21.setValue("50.00");
            lm21.setUnit("%");
            lm21.setStatus("NORMAL");
            lm21.setIcon("water_drop");
            lm21.setSparkline("49.0,50.0,50.2");
            lm21.setThreshold(null);
            lm21.setObjectType("Ambient");

            lab9.getMetrics().addAll(List.of(lm20, lm21));
            laboratoryRepository.save(lab9);

            // Lab 10 Metrics
            var lm22 = new LabMetric();
            lm22.setLaboratory(lab10);
            lm22.setName("temperature");
            lm22.setValue("38.50");
            lm22.setUnit("°C");
            lm22.setStatus("WARNING");
            lm22.setIcon("device_thermostat");
            lm22.setSparkline("37.0,37.5,38.5");
            lm22.setThreshold(37.5);
            lm22.setObjectType("Anaerobic Chamber AC-1");

            var lm23 = new LabMetric();
            lm23.setLaboratory(lab10);
            lm23.setName("co2");
            lm23.setValue("5.00");
            lm23.setUnit("%");
            lm23.setStatus("NORMAL");
            lm23.setIcon("co2");
            lm23.setSparkline("4.8,4.9,5.0");
            lm23.setThreshold(null);
            lm23.setObjectType("Ambient");

            lab10.getMetrics().addAll(List.of(lm22, lm23));
            laboratoryRepository.save(lab10);

            // Lab 11 Metrics
            var lm24 = new LabMetric();
            lm24.setLaboratory(lab11);
            lm24.setName("air_quality");
            lm24.setValue("120.00");
            lm24.setUnit("AQI");
            lm24.setStatus("CRITICAL");
            lm24.setIcon("air");
            lm24.setSparkline("40.0,65.0,90.0,120.0");
            lm24.setThreshold(50.0);
            lm24.setObjectType("Fume Hood F-09");

            var lm25 = new LabMetric();
            lm25.setLaboratory(lab11);
            lm25.setName("pressure");
            lm25.setValue("850.00");
            lm25.setUnit("hPa");
            lm25.setStatus("CRITICAL");
            lm25.setIcon("compress");
            lm25.setSparkline("960.0,910.0,850.0");
            lm25.setThreshold(900.0);
            lm25.setObjectType("Ambient");

            lab11.getMetrics().addAll(List.of(lm24, lm25));
            laboratoryRepository.save(lab11);

            // Lab 12 Metrics
            var lm26 = new LabMetric();
            lm26.setLaboratory(lab12);
            lm26.setName("temperature");
            lm26.setValue("21.00");
            lm26.setUnit("°C");
            lm26.setStatus("NORMAL");
            lm26.setIcon("device_thermostat");
            lm26.setSparkline("20.9,21.0,21.1");
            lm26.setThreshold(null);
            lm26.setObjectType("Ambient");

            var lm27 = new LabMetric();
            lm27.setLaboratory(lab12);
            lm27.setName("vibration");
            lm27.setValue("3.20");
            lm27.setUnit("mm/s");
            lm27.setStatus("WARNING");
            lm27.setIcon("graphic_eq");
            lm27.setSparkline("1.0,1.5,2.4,3.2");
            lm27.setThreshold(3.0);
            lm27.setObjectType("Optical Table OT-04");

            lab12.getMetrics().addAll(List.of(lm26, lm27));
            laboratoryRepository.save(lab12);

            // 12. Seed rich historical metrics readings (last 48 hours, 1-hour intervals)
            var now = LocalDateTime.now();
            List<SensorReading> readingsToSave = new ArrayList<>();
            logger.info("Generating telemetry trend data points...");

            for (int i = 48; i >= 0; i--) {
                var recordedAt = now.minusHours(i);

                // Sensor 1: Temperature T-01 (linked to Refrigerator B2 in Lab 1)
                // Limit: 2.0 to 8.0. Normal ~ 4.5. Critical spike recently (last 3 hours)
                double val1 = 4.5 + Math.sin(i * 0.4) * 1.5;
                if (i <= 2) {
                    val1 = 8.5; // Trigger critical alarm
                }
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor1, lab1, tempType, val1, recordedAt));
                }

                // Sensor 2: Humidity H-02 (ambient in Lab 1)
                // Limit: 30 to 60. Normal ~ 45
                double val2 = 45.0 + Math.sin(i * 0.3) * 8.0;
                if (humidityType != null) {
                    readingsToSave.add(new SensorReading(sensor2, lab1, humidityType, val2, recordedAt));
                }

                // Sensor 3: CO2 C-03 (CO2 Incubator in Lab 2)
                // Limit: 400 to 1000. Normal ~ 600. Warning spike recently (last 4 hours)
                double val3 = 600.0 + Math.sin(i * 0.2) * 150.0;
                if (i <= 4) {
                    val3 = 1050.0; // Trigger warning alarm
                }
                if (co2Type != null) {
                    readingsToSave.add(new SensorReading(sensor3, lab2, co2Type, val3, recordedAt));
                }

                // Sensor 4: LN2 Temperature T-04 (LN2 Tank in Lab 3)
                // Limit: -200 to -180. Normal ~ -190. Critical spike (last 3 hours) to -170.0 (warmer than -180 is breach)
                double val4 = -190.0 + Math.sin(i * 0.3) * 5.0;
                if (i <= 3) {
                    val4 = -170.0; // Trigger critical alarm
                }
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor4, lab3, tempType, val4, recordedAt));
                }

                // Sensor 5: Pressure P-05 (ambient in Lab 3)
                // Limit: 950 to 1050. Normal ~ 1013
                double val5 = 1013.0 + Math.sin(i * 0.1) * 12.0;
                if (pressureType != null) {
                    readingsToSave.add(new SensorReading(sensor5, lab3, pressureType, val5, recordedAt));
                }

                // Sensor 6: Air Quality A-07 (ambient in Lab 2)
                // Limit: 0 to 50. Normal ~ 35
                double val6 = 35.0 + Math.cos(i * 0.45) * 10.0;
                if (airQualityType != null) {
                    readingsToSave.add(new SensorReading(sensor6, lab2, airQualityType, val6, recordedAt));
                }

                // Sensor 7: Vibration V-08 (Reactor in Lab 2)
                // Limit: 0 to 3.0. Normal ~ 1.2. Warning spike (last 6 hours) to 3.5
                double val7 = 1.2 + Math.abs(Math.sin(i * 0.5)) * 0.8;
                if (i <= 6) {
                    val7 = 3.5; // Trigger warning alert
                }
                if (vibrationType != null) {
                    readingsToSave.add(new SensorReading(sensor7, lab2, vibrationType, val7, recordedAt));
                }

                // Sensor 9: Temp in Lab 5 (normal)
                double val9 = 22.0 + Math.sin(i * 0.3) * 1.0;
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor9, lab5, tempType, val9, recordedAt));
                }

                // Sensor 10: Humidity in Lab 5 (normal)
                double val10 = 45.0 + Math.cos(i * 0.2) * 5.0;
                if (humidityType != null) {
                    readingsToSave.add(new SensorReading(sensor10, lab5, humidityType, val10, recordedAt));
                }

                // Sensor 11: Pressure in Lab 6 (critical drop recently, last 5 hours)
                double val11 = 120.0 + Math.sin(i * 0.1) * 10.0;
                if (i <= 5) {
                    val11 = 45.0; // Trigger critical alarm
                }
                if (pressureType != null) {
                    readingsToSave.add(new SensorReading(sensor11, lab6, pressureType, val11, recordedAt));
                }

                // Sensor 12: Air Quality in Lab 6 (critical spike, last 4 hours)
                double val12 = 15.0 + Math.cos(i * 0.3) * 5.0;
                if (i <= 4) {
                    val12 = 75.0; // Trigger critical alarm
                }
                if (airQualityType != null) {
                    readingsToSave.add(new SensorReading(sensor12, lab6, airQualityType, val12, recordedAt));
                }

                // Sensor 13: Temp in Lab 7 (normal)
                double val13 = 20.0 + Math.sin(i * 0.4) * 0.8;
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor13, lab7, tempType, val13, recordedAt));
                }

                // Sensor 14: Pressure in Lab 7 (warning drop, last 8 hours)
                double val14 = 990.0 + Math.sin(i * 0.2) * 15.0;
                if (i <= 8) {
                    val14 = 940.0; // Trigger warning alarm
                }
                if (pressureType != null) {
                    readingsToSave.add(new SensorReading(sensor14, lab7, pressureType, val14, recordedAt));
                }

                // Sensor 15: Temp in Lab 8 (critical temperature spike, last 6 hours)
                double val15 = 20.0 + Math.cos(i * 0.3) * 2.0;
                if (i <= 6) {
                    val15 = 28.5; // Trigger critical alarm
                }
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor15, lab8, tempType, val15, recordedAt));
                }

                // Sensor 16: CO2 in Lab 8 (warning CO2 spike, last 7 hours)
                double val16 = 600.0 + Math.sin(i * 0.25) * 100.0;
                if (i <= 7) {
                    val16 = 1100.0; // Trigger warning alarm
                }
                if (co2Type != null) {
                    readingsToSave.add(new SensorReading(sensor16, lab8, co2Type, val16, recordedAt));
                }

                // Sensor 17: Temp in Lab 9 (warning temp spike, last 9 hours)
                double val17 = 21.0 + Math.cos(i * 0.35) * 1.5;
                if (i <= 9) {
                    val17 = 26.5; // Trigger warning alarm
                }
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor17, lab9, tempType, val17, recordedAt));
                }

                // Sensor 18: Temp in Lab 10 (warning temp spike, last 10 hours)
                double val18 = 36.5 + Math.sin(i * 0.4) * 0.5;
                if (i <= 10) {
                    val18 = 38.5; // Trigger warning alarm
                }
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor18, lab10, tempType, val18, recordedAt));
                }

                // Sensor 19: Air Quality in Lab 11 (critical spike, last 3 hours)
                double val19 = 25.0 + Math.cos(i * 0.3) * 8.0;
                if (i <= 3) {
                    val19 = 120.0; // Trigger critical alarm
                }
                if (airQualityType != null) {
                    readingsToSave.add(new SensorReading(sensor19, lab11, airQualityType, val19, recordedAt));
                }

                // Sensor 20: Pressure in Lab 11 (critical drop, last 4 hours)
                double val20 = 980.0 + Math.sin(i * 0.15) * 10.0;
                if (i <= 4) {
                    val20 = 850.0; // Trigger critical alarm
                }
                if (pressureType != null) {
                    readingsToSave.add(new SensorReading(sensor20, lab11, pressureType, val20, recordedAt));
                }

                // Sensor 21: Vibration in Lab 12 (warning spike, last 12 hours)
                double val21 = 1.0 + Math.abs(Math.sin(i * 0.4)) * 0.8;
                if (i <= 12) {
                    val21 = 3.2; // Trigger warning alarm
                }
                if (vibrationType != null) {
                    readingsToSave.add(new SensorReading(sensor21, lab12, vibrationType, val21, recordedAt));
                }

                // Sensor 22: Temp in Lab 12 (normal)
                double val22 = 20.5 + Math.cos(i * 0.2) * 0.5;
                if (tempType != null) {
                    readingsToSave.add(new SensorReading(sensor22, lab12, tempType, val22, recordedAt));
                }
            }
            sensorReadingRepository.saveAll(readingsToSave);
            logger.info("Saved {} telemetry trend data points.", readingsToSave.size());

            // 13. Seed Alerts
            logger.info("Seeding active and resolved incidents/alerts...");
            // Alert 1: Temperature breach in Refrigerator B2
            var alert1 = new Alert();
            alert1.setLaboratory(lab1);
            alert1.setSensorConfiguration(sensor1);
            alert1.setTitle("Temperature Threshold Exceeded");
            alert1.setDescription("Sensor T-01 registered 8.5°C in Refrigerator B2, exceeding the maximum limit of 8.0°C.");
            alert1.setSeverity("CRITICAL");
            alert1.setStatus("ACTIVE");
            alert1.setLabName(lab1.getName());
            alert1.setTimeAgo("2 hours ago");
            alert1 = alertRepository.save(alert1);

            alert1.getMetrics().addAll(List.of(
                new AlertMetric(alert1, "currentValue", "8.5°C"),
                new AlertMetric(alert1, "threshold", "8.0°C"),
                new AlertMetric(alert1, "exceededBy", "0.5°C"),
                new AlertMetric(alert1, "sensorType", "NTC Thermistor"),
                new AlertMetric(alert1, "lastCalibration", LocalDate.now().minusMonths(3).toString()),
                new AlertMetric(alert1, "signalStrength", "98%"),
                new AlertMetric(alert1, "networkStatus", "ONLINE"),
                new AlertMetric(alert1, "automationRuleName", "Cooling Boost Regulation"),
                new AlertMetric(alert1, "automationRuleStatus", "Running"),
                new AlertMetric(alert1, "automationRuleDesc", "Increases cooling flow when temp limit breaches.")
            ));
            alertRepository.save(alert1);

            // Alert 2: LN2 Temperature critical breach
            var alert2 = new Alert();
            alert2.setLaboratory(lab3);
            alert2.setSensorConfiguration(sensor4);
            alert2.setTitle("Cryo LN2 Temperature Critical Alert");
            alert2.setDescription("Sensor T-04 registered -170.0°C in LN2 Tank Cryo-01, warmer than the critical limit of -180.0°C.");
            alert2.setSeverity("CRITICAL");
            alert2.setStatus("ACTIVE");
            alert2.setLabName(lab3.getName());
            alert2.setTimeAgo("3 hours ago");
            alert2 = alertRepository.save(alert2);

            alert2.getMetrics().addAll(List.of(
                new AlertMetric(alert2, "currentValue", "-170.0°C"),
                new AlertMetric(alert2, "threshold", "-180.0°C"),
                new AlertMetric(alert2, "exceededBy", "10.0°C"),
                new AlertMetric(alert2, "sensorType", "Cryogenic RTD Probe"),
                new AlertMetric(alert2, "lastCalibration", LocalDate.now().minusMonths(2).toString()),
                new AlertMetric(alert2, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert2);

            // Alert 3: CO2 Warning Level
            var alert3 = new Alert();
            alert3.setLaboratory(lab2);
            alert3.setSensorConfiguration(sensor3);
            alert3.setTitle("CO2 Level Warning");
            alert3.setDescription("Sensor C-03 registered 1050 ppm, warning threshold is 1000 ppm.");
            alert3.setSeverity("WARNING");
            alert3.setStatus("ACTIVE");
            alert3.setLabName(lab2.getName());
            alert3.setTimeAgo("4 hours ago");
            alert3 = alertRepository.save(alert3);

            alert3.getMetrics().addAll(List.of(
                new AlertMetric(alert3, "currentValue", "1050 ppm"),
                new AlertMetric(alert3, "threshold", "1000 ppm"),
                new AlertMetric(alert3, "exceededBy", "50 ppm"),
                new AlertMetric(alert3, "sensorType", "NIDR CO2 Sensor")
            ));
            alertRepository.save(alert3);

            // Alert 4: Vibration Warning
            var alert4 = new Alert();
            alert4.setLaboratory(lab2);
            alert4.setSensorConfiguration(sensor7);
            alert4.setTitle("Reactor Vibration Spike");
            alert4.setDescription("Sensor V-08 registered 3.5 mm/s, warning threshold is 3.0 mm/s.");
            alert4.setSeverity("WARNING");
            alert4.setStatus("ACTIVE");
            alert4.setLabName(lab2.getName());
            alert4.setTimeAgo("6 hours ago");
            alert4 = alertRepository.save(alert4);

            alert4.getMetrics().addAll(List.of(
                new AlertMetric(alert4, "currentValue", "3.5 mm/s"),
                new AlertMetric(alert4, "threshold", "3.0 mm/s"),
                new AlertMetric(alert4, "exceededBy", "0.5 mm/s")
            ));
            alertRepository.save(alert4);

            // Alert 5: Resolved Alert
            var alert5 = new Alert();
            alert5.setLaboratory(lab1);
            alert5.setSensorConfiguration(sensor2);
            alert5.setTitle("Humidity Levels Restored");
            alert5.setDescription("Sensor H-02 registered 62.0% (Limit: 60.0%) but returned to normal range (42.5%).");
            alert5.setSeverity("WARNING");
            alert5.setStatus("RESOLVED");
            alert5.setLabName(lab1.getName());
            alert5.setTimeAgo("1 day ago");
            alert5 = alertRepository.save(alert5);

            alert5.getMetrics().addAll(List.of(
                new AlertMetric(alert5, "currentValue", "42.5%"),
                new AlertMetric(alert5, "exceededValue", "62.0%"),
                new AlertMetric(alert5, "threshold", "60.0%")
            ));
            alertRepository.save(alert5);

            // Alert 6: HEPA Filter Pressure Failure (Critical, Active, Lab 6)
            var alert6 = new Alert();
            alert6.setLaboratory(lab6);
            alert6.setSensorConfiguration(sensor11);
            alert6.setTitle("HEPA Filter Pressure Failure");
            alert6.setDescription("Sensor P-11 registered 45.0 Pa in HEPA Filtration Unit H-1, below the minimum differential pressure of 50.0 Pa.");
            alert6.setSeverity("CRITICAL");
            alert6.setStatus("ACTIVE");
            alert6.setLabName(lab6.getName());
            alert6.setTimeAgo("5 hours ago");
            alert6 = alertRepository.save(alert6);

            alert6.getMetrics().addAll(List.of(
                new AlertMetric(alert6, "currentValue", "45.0 Pa"),
                new AlertMetric(alert6, "threshold", "50.0 Pa"),
                new AlertMetric(alert6, "exceededBy", "-5.0 Pa"),
                new AlertMetric(alert6, "sensorType", "Differential Pressure Transducer"),
                new AlertMetric(alert6, "lastCalibration", LocalDate.now().minusMonths(1).toString()),
                new AlertMetric(alert6, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert6);

            // Alert 7: Clean Room Air Quality Hazard (Critical, Active, Lab 6)
            var alert7 = new Alert();
            alert7.setLaboratory(lab6);
            alert7.setSensorConfiguration(sensor12);
            alert7.setTitle("Clean Room Air Quality Hazard");
            alert7.setDescription("Sensor A-12 registered 75.0 AQI in Clean Room Class 100, exceeding the strict room limit of 50.0 AQI.");
            alert7.setSeverity("CRITICAL");
            alert7.setStatus("ACTIVE");
            alert7.setLabName(lab6.getName());
            alert7.setTimeAgo("4 hours ago");
            alert7 = alertRepository.save(alert7);

            alert7.getMetrics().addAll(List.of(
                new AlertMetric(alert7, "currentValue", "75.0 AQI"),
                new AlertMetric(alert7, "threshold", "50.0 AQI"),
                new AlertMetric(alert7, "exceededBy", "25.0 AQI"),
                new AlertMetric(alert7, "sensorType", "Particulate Matter Laser Counter"),
                new AlertMetric(alert7, "lastCalibration", LocalDate.now().minusMonths(4).toString()),
                new AlertMetric(alert7, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert7);

            // Alert 8: Toxic Gas/Air Quality Spill (Critical, Active, Lab 11)
            var alert8 = new Alert();
            alert8.setLaboratory(lab11);
            alert8.setSensorConfiguration(sensor19);
            alert8.setTitle("Toxic Gas Air Quality Spike");
            alert8.setDescription("Sensor A-19 registered 120.0 AQI in Fume Hood F-09, indicating a potential volatile organic compound leak.");
            alert8.setSeverity("CRITICAL");
            alert8.setStatus("ACTIVE");
            alert8.setLabName(lab11.getName());
            alert8.setTimeAgo("3 hours ago");
            alert8 = alertRepository.save(alert8);

            alert8.getMetrics().addAll(List.of(
                new AlertMetric(alert8, "currentValue", "120.0 AQI"),
                new AlertMetric(alert8, "threshold", "50.0 AQI"),
                new AlertMetric(alert8, "exceededBy", "70.0 AQI"),
                new AlertMetric(alert8, "sensorType", "Metal Oxide Gas Sensor"),
                new AlertMetric(alert8, "lastCalibration", LocalDate.now().minusMonths(3).toString()),
                new AlertMetric(alert8, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert8);

            // Alert 9: Negative Pressure Loss (Critical, Active, Lab 11)
            var alert9 = new Alert();
            alert9.setLaboratory(lab11);
            alert9.setSensorConfiguration(sensor20);
            alert9.setTitle("Negative Pressure Containment Loss");
            alert9.setDescription("Sensor P-20 registered 850.0 hPa, warmer/higher than negative pressure critical threshold of 900.0 hPa.");
            alert9.setSeverity("CRITICAL");
            alert9.setStatus("ACTIVE");
            alert9.setLabName(lab11.getName());
            alert9.setTimeAgo("4 hours ago");
            alert9 = alertRepository.save(alert9);

            alert9.getMetrics().addAll(List.of(
                new AlertMetric(alert9, "currentValue", "850.0 hPa"),
                new AlertMetric(alert9, "threshold", "900.0 hPa"),
                new AlertMetric(alert9, "exceededBy", "-50.0 hPa"),
                new AlertMetric(alert9, "sensorType", "Barometric Pressure Sensor"),
                new AlertMetric(alert9, "lastCalibration", LocalDate.now().minusMonths(1).toString()),
                new AlertMetric(alert9, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert9);

            // Alert 10: Incubator Temperature Deviation (Warning, Active, Lab 10)
            var alert10 = new Alert();
            alert10.setLaboratory(lab10);
            alert10.setSensorConfiguration(sensor18);
            alert10.setTitle("Incubator Temperature Deviation");
            alert10.setDescription("Sensor T-18 registered 38.5°C in Anaerobic Chamber AC-1, exceeding warning threshold of 37.5°C.");
            alert10.setSeverity("WARNING");
            alert10.setStatus("ACTIVE");
            alert10.setLabName(lab10.getName());
            alert10.setTimeAgo("10 hours ago");
            alert10 = alertRepository.save(alert10);

            alert10.getMetrics().addAll(List.of(
                new AlertMetric(alert10, "currentValue", "38.5°C"),
                new AlertMetric(alert10, "threshold", "37.5°C"),
                new AlertMetric(alert10, "exceededBy", "1.0°C"),
                new AlertMetric(alert10, "sensorType", "RTD Probe"),
                new AlertMetric(alert10, "lastCalibration", LocalDate.now().minusMonths(2).toString()),
                new AlertMetric(alert10, "networkStatus", "ONLINE")
            ));
            alertRepository.save(alert10);

            // Alert 11: Vibration Threshold Warning (Warning, Active, Lab 12)
            var alert11 = new Alert();
            alert11.setLaboratory(lab12);
            alert11.setSensorConfiguration(sensor21);
            alert11.setTitle("Laser Bench Vibration Warning");
            alert11.setDescription("Sensor V-21 registered 3.2 mm/s on Optical Table OT-04, exceeding warning limit of 3.0 mm/s.");
            alert11.setSeverity("WARNING");
            alert11.setStatus("ACTIVE");
            alert11.setLabName(lab12.getName());
            alert11.setTimeAgo("12 hours ago");
            alert11 = alertRepository.save(alert11);

            alert11.getMetrics().addAll(List.of(
                new AlertMetric(alert11, "currentValue", "3.2 mm/s"),
                new AlertMetric(alert11, "threshold", "3.0 mm/s"),
                new AlertMetric(alert11, "exceededBy", "0.2 mm/s"),
                new AlertMetric(alert11, "sensorType", "Piezoelectric Accelerometer"),
                new AlertMetric(alert11, "lastCalibration", LocalDate.now().minusMonths(2).toString())
            ));
            alertRepository.save(alert11);

            // Alert 12: Low Room Pressure Warning (Warning, Active, Lab 7)
            var alert12 = new Alert();
            alert12.setLaboratory(lab7);
            alert12.setSensorConfiguration(sensor14);
            alert12.setTitle("Low Room Pressure Warning");
            alert12.setDescription("Sensor P-14 registered 940.0 hPa in Radiation Facility, below warning threshold of 950.0 hPa.");
            alert12.setSeverity("WARNING");
            alert12.setStatus("ACTIVE");
            alert12.setLabName(lab7.getName());
            alert12.setTimeAgo("8 hours ago");
            alert12 = alertRepository.save(alert12);

            alert12.getMetrics().addAll(List.of(
                new AlertMetric(alert12, "currentValue", "940.0 hPa"),
                new AlertMetric(alert12, "threshold", "950.0 hPa"),
                new AlertMetric(alert12, "exceededBy", "-10.0 hPa")
            ));
            alertRepository.save(alert12);

            // Alert 13: Room Temperature Warning (Warning, Active, Lab 9)
            var alert13 = new Alert();
            alert13.setLaboratory(lab9);
            alert13.setSensorConfiguration(sensor17);
            alert13.setTitle("Sequencing Room Temp Warning");
            alert13.setDescription("Sensor T-17 registered 26.5°C on Sequencer Seq-02, exceeding warning limit of 25.0°C.");
            alert13.setSeverity("WARNING");
            alert13.setStatus("ACTIVE");
            alert13.setLabName(lab9.getName());
            alert13.setTimeAgo("9 hours ago");
            alert13 = alertRepository.save(alert13);

            alert13.getMetrics().addAll(List.of(
                new AlertMetric(alert13, "currentValue", "26.5°C"),
                new AlertMetric(alert13, "threshold", "25.0°C"),
                new AlertMetric(alert13, "exceededBy", "1.5°C")
            ));
            alertRepository.save(alert13);

            // Alert 14: Power Interruption Restored (Critical, Resolved, Lab 5)
            var alert14 = new Alert();
            alert14.setLaboratory(lab5);
            alert14.setSensorConfiguration(sensor9);
            alert14.setTitle("Power Interruption Restored");
            alert14.setDescription("Primary power grid connection lost but secondary backup generator triggered; main power successfully restored.");
            alert14.setSeverity("CRITICAL");
            alert14.setStatus("RESOLVED");
            alert14.setLabName(lab5.getName());
            alert14.setTimeAgo("18 hours ago");
            alert14 = alertRepository.save(alert14);

            alert14.getMetrics().addAll(List.of(
                new AlertMetric(alert14, "duration", "15 minutes"),
                new AlertMetric(alert14, "backupStatus", "DISENGAGED"),
                new AlertMetric(alert14, "currentState", "STABLE")
            ));
            alertRepository.save(alert14);

            // Alert 15: Anaerobic O2 Concentration Normalized (Warning, Resolved, Lab 10)
            var alert15 = new Alert();
            alert15.setLaboratory(lab10);
            alert15.setSensorConfiguration(sensor18);
            alert15.setTitle("O2 Concentration Normalized");
            alert15.setDescription("O2 concentration exceeded warning threshold of 2.0% but returned to stable 1.5% after chamber flush.");
            alert15.setSeverity("WARNING");
            alert15.setStatus("RESOLVED");
            alert15.setLabName(lab10.getName());
            alert15.setTimeAgo("1 day ago");
            alert15 = alertRepository.save(alert15);

            alert15.getMetrics().addAll(List.of(
                new AlertMetric(alert15, "currentValue", "1.5%"),
                new AlertMetric(alert15, "exceededValue", "4.8%"),
                new AlertMetric(alert15, "threshold", "2.0%")
            ));
            alertRepository.save(alert15);

            // Alert 16: Incubator Temp Alarm Resolved (Critical, Resolved, Lab 1)
            var alert16 = new Alert();
            alert16.setLaboratory(lab1);
            alert16.setSensorConfiguration(sensor1);
            alert16.setTitle("Incubator Temp Alarm Resolved");
            alert16.setDescription("Temperature anomaly resolved. Ambient temp returned to normal range (5.2°C).");
            alert16.setSeverity("CRITICAL");
            alert16.setStatus("RESOLVED");
            alert16.setLabName(lab1.getName());
            alert16.setTimeAgo("1 day ago");
            alert16 = alertRepository.save(alert16);

            alert16.getMetrics().addAll(List.of(
                new AlertMetric(alert16, "currentValue", "5.2°C"),
                new AlertMetric(alert16, "threshold", "8.0°C")
            ));
            alertRepository.save(alert16);

            // Alert 17: Vibration Peak Dampened (Warning, Resolved, Lab 2)
            var alert17 = new Alert();
            alert17.setLaboratory(lab2);
            alert17.setSensorConfiguration(sensor7);
            alert17.setTitle("Vibration Peak Dampened");
            alert17.setDescription("Vibration anomaly on Reactor R-02 resolved; current levels stable at 1.2 mm/s.");
            alert17.setSeverity("WARNING");
            alert17.setStatus("RESOLVED");
            alert17.setLabName(lab2.getName());
            alert17.setTimeAgo("1 day ago");
            alert17 = alertRepository.save(alert17);

            alert17.getMetrics().addAll(List.of(
                new AlertMetric(alert17, "currentValue", "1.2 mm/s"),
                new AlertMetric(alert17, "threshold", "3.0 mm/s")
            ));
            alertRepository.save(alert17);

            // Alert 18: Pressure Restored in Cryo Room (Warning, Resolved, Lab 3)
            var alert18 = new Alert();
            alert18.setLaboratory(lab3);
            alert18.setSensorConfiguration(sensor5);
            alert18.setTitle("Cryo Room Pressure Restored");
            alert18.setDescription("Pressure sensor fluctuation resolved. Ambient pressure normalized to 1012 hPa.");
            alert18.setSeverity("WARNING");
            alert18.setStatus("RESOLVED");
            alert18.setLabName(lab3.getName());
            alert18.setTimeAgo("2 days ago");
            alert18 = alertRepository.save(alert18);

            alert18.getMetrics().addAll(List.of(
                new AlertMetric(alert18, "currentValue", "1012 hPa"),
                new AlertMetric(alert18, "threshold", "950 hPa")
            ));
            alertRepository.save(alert18);

            logger.info("Active and resolved alerts seeded successfully.");

            // 14. Seed History Records (Audit Trail)
            logger.info("Seeding history audit trail records...");
            historyRecordRepository.save(new HistoryRecord(lab1, "Temperature Critical Alarm", "Sensor T-01 registered 8.5°C (Limit: 8.0°C) in Refrigerator B2.", LocalDateTime.now().minusHours(2), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab1, "Automation rule triggered", "Automation: Cooling Boost Regulation activated for zone Bio-Safety Lab 04.", LocalDateTime.now().minusHours(2).plusMinutes(1), "automation", "Info", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab3, "Cryo LN2 Critical Alert", "Sensor T-04 registered -170.0°C in LN2 Tank Cryo-01.", LocalDateTime.now().minusHours(3), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab2, "CO2 Warning Active", "CO2 level exceeded warning threshold (1050 ppm).", LocalDateTime.now().minusHours(4), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab2, "Vibration Spike Detected", "Vibration level exceeded warning limit on Reactor R-02 (3.5 mm/s).", LocalDateTime.now().minusHours(6), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab1, "Sensor H-02 Warning Resolved", "Humidity levels returned to normal range (42.5%).", LocalDateTime.now().minusDays(1), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab1, "Manual safety audit logged", "Technician validated secondary cooling pressure gauge. Value: 3.2 bar (Normal).", LocalDateTime.now().minusHours(12), "observation", "Info", "Normal"));
            historyRecordRepository.save(new HistoryRecord(lab4, "HPLC Column Calibrated", "Analytical GC-MS column successfully calibrated by supervisor.", LocalDateTime.now().minusDays(1).minusHours(4), "maintenance", "Info", "Completed"));
            historyRecordRepository.save(new HistoryRecord(lab3, "Pressure check completed", "Cryo room safety valve inspection successfully completed.", LocalDateTime.now().minusDays(2), "maintenance", "Info", "Completed"));
            historyRecordRepository.save(new HistoryRecord(lab2, "Workspace access updated", "Assigned Operator role access permissions to SafeLab Operator.", LocalDateTime.now().minusDays(2).minusHours(1), "system", "Info", "Completed"));
            historyRecordRepository.save(new HistoryRecord(lab6, "HEPA Filter Pressure Failure", "Sensor P-11 registered 45.0 Pa (Limit: 50.0 Pa) in Clean Room.", LocalDateTime.now().minusHours(5), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab6, "Clean Room Air Quality Hazard", "Sensor A-12 registered 75.0 AQI (Limit: 50.0 AQI).", LocalDateTime.now().minusHours(4), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab11, "Toxic Gas Air Quality Spike", "Sensor A-19 registered 120.0 AQI (Limit: 50.0 AQI) in Fume Hood.", LocalDateTime.now().minusHours(3), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab11, "Negative Pressure Containment Loss", "Sensor P-20 registered 850.0 hPa (Limit: 900.0 hPa).", LocalDateTime.now().minusHours(4), "incident", "Critical", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab10, "Incubator Temperature Deviation", "Sensor T-18 registered 38.5°C (Limit: 37.5°C) in AC-1.", LocalDateTime.now().minusHours(10), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab12, "Laser Bench Vibration Warning", "Sensor V-21 registered 3.2 mm/s on Optical Table.", LocalDateTime.now().minusHours(12), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab7, "Low Room Pressure Warning", "Sensor P-14 registered 940.0 hPa in Radiation Facility.", LocalDateTime.now().minusHours(8), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab9, "Sequencing Room Temp Warning", "Sensor T-17 registered 26.5°C (Limit: 25.0°C).", LocalDateTime.now().minusHours(9), "incident", "Warning", "Active"));
            historyRecordRepository.save(new HistoryRecord(lab5, "Power Interruption Resolved", "Backup generator disengaged, grid power restored successfully.", LocalDateTime.now().minusHours(18), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab10, "O2 Concentration Normalized", "Oxygen levels returned to normal 1.5%.", LocalDateTime.now().minusDays(1), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab1, "Incubator Temp Alarm Resolved", "Temperature returned to normal range (5.2°C).", LocalDateTime.now().minusDays(1), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab2, "Vibration Peak Dampened", "Vibration level stabilized at 1.2 mm/s on Reactor.", LocalDateTime.now().minusDays(1), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab3, "Cryo Room Pressure Restored", "Flashing pressure resolved, stabilized at 1012 hPa.", LocalDateTime.now().minusDays(2), "incident", "Info", "Resolved"));
            historyRecordRepository.save(new HistoryRecord(lab5, "PCR Thermocycler Inspection", "Routine safety review completed on PCR Thermocycler T-12.", LocalDateTime.now().minusHours(14), "maintenance", "Info", "Completed"));
            historyRecordRepository.save(new HistoryRecord(lab6, "HEPA Filter Replacement", "HEPA primary stage filters scheduled for replacement next week.", LocalDateTime.now().minusDays(2), "maintenance", "Info", "Scheduled"));
            logger.info("History records seeded successfully.");

            // 15. Seed Compliance Reports
            logger.info("Seeding compliance reports...");
            var report1 = new ComplianceReport();
            report1.setLaboratory(lab1);
            report1.setName("Environmental Compliance Audit");
            report1.setType("Safety Compliance");
            report1.setPeriod("May 2026");
            report1.setSize("2.4 MB");
            report1.setStatus("READY");
            report1.setPeriodStart(LocalDateTime.now().minusMonths(1).minusDays(10));
            report1.setPeriodEnd(LocalDateTime.now().minusMonths(1));
            report1.setGeneratedAt(LocalDateTime.now().minusDays(5));
            complianceReportRepository.save(report1);

            var report2 = new ComplianceReport();
            report2.setLaboratory(lab1);
            report2.setName("Ventilation and HEPA Log");
            report2.setType("System Log");
            report2.setPeriod("June 2026");
            report2.setSize("1.8 MB");
            report2.setStatus("READY");
            report2.setPeriodStart(LocalDateTime.now().minusDays(20));
            report2.setPeriodEnd(LocalDateTime.now().minusDays(5));
            report2.setGeneratedAt(LocalDateTime.now().minusDays(2));
            complianceReportRepository.save(report2);

            var report3 = new ComplianceReport();
            report3.setLaboratory(lab2);
            report3.setName("Chemical Storage Safety Assessment");
            report3.setType("Safety Compliance");
            report3.setPeriod("Q2 2026");
            report3.setSize("4.1 MB");
            report3.setStatus("READY");
            report3.setPeriodStart(LocalDateTime.now().minusMonths(3));
            report3.setPeriodEnd(LocalDateTime.now());
            report3.setGeneratedAt(LocalDateTime.now().minusDays(1));
            complianceReportRepository.save(report3);

            logger.info("Compliance reports seeded successfully.");
            saveMaxSeededIds();
        }
    }

    private void saveMaxSeededIds() {
        logger.info("Saving maximum seeded IDs to general settings...");
        saveMaxIdSetting("max_seeded_user_id", "users");
        saveMaxIdSetting("max_seeded_profile_id", "user_profiles");
        saveMaxIdSetting("max_seeded_workspace_access_id", "user_workspace_accesses");
        saveMaxIdSetting("max_seeded_laboratory_id", "laboratories");
        saveMaxIdSetting("max_seeded_workspace_id", "workspaces");
        saveMaxIdSetting("max_seeded_sensor_id", "sensor_configurations");
        saveMaxIdSetting("max_seeded_reading_id", "sensor_readings");
        saveMaxIdSetting("max_seeded_alert_id", "alerts");
        saveMaxIdSetting("max_seeded_history_id", "history_records");
        saveMaxIdSetting("max_seeded_lab_access_id", "lab_user_accesses");
        saveMaxIdSetting("max_seeded_notification_pref_id", "notification_preferences");
        saveMaxIdSetting("max_seeded_storage_unit_id", "storage_units");
        saveMaxIdSetting("max_seeded_mitigation_system_id", "mitigation_systems");
        saveMaxIdSetting("max_seeded_metric_sub_id", "lab_metric_subscriptions");
        saveMaxIdSetting("max_seeded_metric_id", "lab_metrics");
        saveMaxIdSetting("max_seeded_activity_id", "lab_activities");
        saveMaxIdSetting("max_seeded_schedule_id", "lab_schedules");
        saveMaxIdSetting("max_seeded_equipment_threshold_id", "equipment_thresholds");
        saveMaxIdSetting("max_seeded_calibration_record_id", "calibration_records");
        saveMaxIdSetting("max_seeded_alert_metric_id", "alert_metrics");
        saveMaxIdSetting("max_seeded_compliance_report_id", "compliance_reports");
    }

    private void saveMaxIdSetting(String settingKey, String tableName) {
        try {
            Object result = entityManager.createNativeQuery("SELECT MAX(id) FROM " + tableName).getSingleResult();
            String val = result != null ? result.toString() : "0";
            generalSettingRepository.findBySettingKey(settingKey)
                .ifPresentOrElse(
                    setting -> {
                        setting.setValue(val);
                        generalSettingRepository.save(setting);
                    },
                    () -> {
                        generalSettingRepository.save(new GeneralSetting(settingKey, val, "max_seeded_ids", "Maximum seeded ID for " + tableName));
                    }
                );
        } catch (Exception e) {
            logger.error("Failed to save max ID setting for table " + tableName, e);
        }
    }
}
