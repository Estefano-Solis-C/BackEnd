package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.application.internal.outboundservices.email.EmailService;
import com.test.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.test.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.ConfirmAccountCommand;
import com.test.backend.iam.domain.model.commands.ResendConfirmationCodeCommand;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.commands.UpdatePasswordCommand;
import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.events.UserRegisteredEvent;
import com.test.backend.iam.domain.model.exceptions.UserNotConfirmedException;
import com.test.backend.iam.domain.model.valueobjects.AuthenticatedUser;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.IamRoleRepository;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private static final Logger logger = LoggerFactory.getLogger(UserCommandServiceImpl.class);

    private final UserRepository userRepository;
    private final IamRoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final EmailService emailService;
    private final EntityManager entityManager;

    public UserCommandServiceImpl(UserRepository userRepository, IamRoleRepository roleRepository,
                                   HashingService hashingService, TokenService tokenService,
                                   ApplicationEventPublisher eventPublisher,
                                   EmailService emailService,
                                   EntityManager entityManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.emailService = emailService;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<AuthenticatedUser> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            return Optional.empty();
        }
        if (!user.get().isConfirmed()) {
            throw new UserNotConfirmedException("User account is not confirmed");
        }
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(new AuthenticatedUser(user.get(), token));
    }


    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            var existingUser = userRepository.findByEmail(command.email()).orElseThrow();
            if (!existingUser.isConfirmed()) {
                throw new RuntimeException("EMAIL_ALREADY_EXISTS_UNCONFIRMED");
            }
            throw new RuntimeException("EMAIL_ALREADY_EXISTS");
        }

        var roles = new ArrayList<Role>();
        if (command.roles() == null || command.roles().isEmpty()) {
            roleRepository.findByName(Roles.ROLE_USER).ifPresent(roles::add);
        } else {
            command.roles().forEach(roleName -> {
                roleRepository.findByName(roleName).ifPresent(roles::add);
            });
        }

        var user = new User(
                command.email(),
                hashingService.encode(command.password()),
                command.fullName(),
                roles
        );

        // Special handling for the demo seeded user admin@safelab.com
        if ("admin@safelab.com".equals(command.email())) {
            user.setConfirmed(true);
            userRepository.save(user);
        } else {
            String code = generateConfirmationCode();
            user.setConfirmationCode(code);
            user.setConfirmed(false);
            userRepository.save(user);
            emailService.sendVerificationEmail(user.getEmail(), user.getFullName(), code);
        }

        eventPublisher.publishEvent(new UserRegisteredEvent(user.getEmail(), user.getFullName()));
        return Optional.of(user);
    }

    @Override
    public boolean handle(ConfirmAccountCommand command) {
        var userOptional = userRepository.findByEmail(command.email());
        if (userOptional.isEmpty()) {
            return false;
        }
        var user = userOptional.get();
        if (user.isConfirmed()) {
            return true;
        }
        if (user.getConfirmationCode() != null && user.getConfirmationCode().equals(command.code())) {
            user.setConfirmed(true);
            user.setConfirmationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean handle(ResendConfirmationCodeCommand command) {
        var userOptional = userRepository.findByEmail(command.email());
        if (userOptional.isEmpty()) {
            return false;
        }
        var user = userOptional.get();
        if (user.isConfirmed()) {
            return false;
        }
        String code = generateConfirmationCode();
        user.setConfirmationCode(code);
        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), user.getFullName(), code);
        return true;
    }

    private String generateConfirmationCode() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    @Override
    public boolean handle(UpdatePasswordCommand command) {
        var userOptional = userRepository.findByEmail(command.email());
        if (userOptional.isEmpty()) {
            return false;
        }
        var user = userOptional.get();
        if (!hashingService.matches(command.currentPassword(), user.getPassword())) {
            return false;
        }
        user.setPassword(hashingService.encode(command.newPassword()));
        userRepository.save(user);
        return true;
    }

    private Long getMaxSeededId(String settingKey) {
        try {
            Object val = entityManager.createNativeQuery(
                "SELECT value FROM general_settings WHERE setting_key = ?1"
            ).setParameter(1, settingKey).getSingleResult();
            if (val != null) {
                return Long.parseLong(val.toString());
            }
        } catch (Exception e) {
            // Setting doesn't exist yet, fallback to 0
        }
        return 0L;
    }

    @Override
    @Transactional
    public void handleCleanTestUsers() {
        String adminEmail = "admin@safelab.com";

        Long maxSeededUserId = getMaxSeededId("max_seeded_user_id");
        if (maxSeededUserId == 0L) {
            try {
                Object adminId = entityManager.createNativeQuery("SELECT id FROM users WHERE email = ?1").setParameter(1, adminEmail).getSingleResult();
                if (adminId != null) maxSeededUserId = Long.parseLong(adminId.toString());
            } catch (Exception e) {}
        }

        Long maxSeededProfileId = getMaxSeededId("max_seeded_profile_id");
        if (maxSeededProfileId == 0L) {
            try {
                Object pId = entityManager.createNativeQuery("SELECT id FROM user_profiles WHERE email = ?1").setParameter(1, adminEmail).getSingleResult();
                if (pId != null) maxSeededProfileId = Long.parseLong(pId.toString());
            } catch (Exception e) {}
        }

        Long maxSeededLabId = getMaxSeededId("max_seeded_laboratory_id");
        if (maxSeededLabId == 0L) {
            try {
                Object maxLabId = entityManager.createNativeQuery("SELECT MAX(id) FROM laboratories WHERE lab_code IN ('LAB-BSL4-01','LAB-CHEM-02','LAB-CRYO-03','LAB-ANAL-04','LAB-MOLB-05','LAB-CLEAN-06','LAB-RAD-07','LAB-PHARM-08','LAB-GENE-09','LAB-MICRO-10')").getSingleResult();
                if (maxLabId != null) maxSeededLabId = Long.parseLong(maxLabId.toString());
            } catch (Exception e) {}
        }

        Long maxSeededReadingId = getMaxSeededId("max_seeded_reading_id");
        Long maxSeededAlertId = getMaxSeededId("max_seeded_alert_id");
        Long maxSeededAlertMetricId = getMaxSeededId("max_seeded_alert_metric_id");
        Long maxSeededHistoryId = getMaxSeededId("max_seeded_history_id");
        Long maxSeededSensorId = getMaxSeededId("max_seeded_sensor_id");
        Long maxSeededCalibrationId = getMaxSeededId("max_seeded_calibration_record_id");
        Long maxSeededWorkspaceAccessId = getMaxSeededId("max_seeded_workspace_access_id");
        Long maxSeededLabAccessId = getMaxSeededId("max_seeded_lab_access_id");
        Long maxSeededNotificationPrefId = getMaxSeededId("max_seeded_notification_pref_id");
        Long maxSeededStorageUnitId = getMaxSeededId("max_seeded_storage_unit_id");
        Long maxSeededMitigationSystemId = getMaxSeededId("max_seeded_mitigation_system_id");
        Long maxSeededMetricSubId = getMaxSeededId("max_seeded_metric_sub_id");
        Long maxSeededMetricId = getMaxSeededId("max_seeded_metric_id");
        Long maxSeededActivityId = getMaxSeededId("max_seeded_activity_id");
        Long maxSeededScheduleId = getMaxSeededId("max_seeded_schedule_id");
        Long maxSeededEquipmentThresholdId = getMaxSeededId("max_seeded_equipment_threshold_id");
        Long maxSeededWorkspaceId = getMaxSeededId("max_seeded_workspace_id");

        // Native deletions in exact dependency order:

        // 1. calibration_records
        entityManager.createNativeQuery("DELETE FROM calibration_records WHERE id > ?1").setParameter(1, maxSeededCalibrationId).executeUpdate();

        // 2. sensor_readings
        entityManager.createNativeQuery("DELETE FROM sensor_readings WHERE id > ?1").setParameter(1, maxSeededReadingId).executeUpdate();

        // 3. alert_metrics
        entityManager.createNativeQuery("DELETE FROM alert_metrics WHERE id > ?1").setParameter(1, maxSeededAlertMetricId).executeUpdate();

        // 4. alerts
        entityManager.createNativeQuery("DELETE FROM alerts WHERE id > ?1").setParameter(1, maxSeededAlertId).executeUpdate();

        // 5. mitigation_systems
        entityManager.createNativeQuery("DELETE FROM mitigation_systems WHERE id > ?1").setParameter(1, maxSeededMitigationSystemId).executeUpdate();

        // 6. storage_units
        entityManager.createNativeQuery("DELETE FROM storage_units WHERE id > ?1").setParameter(1, maxSeededStorageUnitId).executeUpdate();

        // 7. lab_metric_subscriptions
        entityManager.createNativeQuery("DELETE FROM lab_metric_subscriptions WHERE id > ?1").setParameter(1, maxSeededMetricSubId).executeUpdate();

        // 8. lab_metrics
        entityManager.createNativeQuery("DELETE FROM lab_metrics WHERE id > ?1").setParameter(1, maxSeededMetricId).executeUpdate();

        // 9. lab_activities
        entityManager.createNativeQuery("DELETE FROM lab_activities WHERE id > ?1").setParameter(1, maxSeededActivityId).executeUpdate();

        // 10. lab_schedules
        entityManager.createNativeQuery("DELETE FROM lab_schedules WHERE id > ?1").setParameter(1, maxSeededScheduleId).executeUpdate();

        // 11. history_records
        entityManager.createNativeQuery("DELETE FROM history_records WHERE id > ?1").setParameter(1, maxSeededHistoryId).executeUpdate();

        // 12. sensor_configurations
        entityManager.createNativeQuery("DELETE FROM sensor_configurations WHERE id > ?1").setParameter(1, maxSeededSensorId).executeUpdate();

        // 13. equipment_thresholds
        entityManager.createNativeQuery("DELETE FROM equipment_thresholds WHERE id > ?1").setParameter(1, maxSeededEquipmentThresholdId).executeUpdate();

        // 14. lab_user_accesses
        entityManager.createNativeQuery("DELETE FROM lab_user_accesses WHERE id > ?1").setParameter(1, maxSeededLabAccessId).executeUpdate();

        // 15. user_workspace_accesses
        entityManager.createNativeQuery("DELETE FROM user_workspace_accesses WHERE id > ?1").setParameter(1, maxSeededWorkspaceAccessId).executeUpdate();

        // 16. notification_preferences
        entityManager.createNativeQuery("DELETE FROM notification_preferences WHERE id > ?1").setParameter(1, maxSeededNotificationPrefId).executeUpdate();

        // 17. user_profiles
        entityManager.createNativeQuery("DELETE FROM user_profiles WHERE id > ?1").setParameter(1, maxSeededProfileId).executeUpdate();

        // 18. user_iam_roles
        entityManager.createNativeQuery("DELETE FROM user_iam_roles WHERE user_id > ?1").setParameter(1, maxSeededUserId).executeUpdate();

        // 19. users
        entityManager.createNativeQuery("DELETE FROM users WHERE id > ?1").setParameter(1, maxSeededUserId).executeUpdate();

        // 20. laboratories
        entityManager.createNativeQuery("DELETE FROM laboratories WHERE id > ?1").setParameter(1, maxSeededLabId).executeUpdate();

        // 21. workspaces
        entityManager.createNativeQuery("DELETE FROM workspaces WHERE id > ?1").setParameter(1, maxSeededWorkspaceId).executeUpdate();

        logger.info("Successfully performed pristine database reset. Seeded data is preserved.");
    }
}
