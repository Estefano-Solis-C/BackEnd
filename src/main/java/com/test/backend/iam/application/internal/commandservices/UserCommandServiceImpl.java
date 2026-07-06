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

    @Override
    @Transactional
    public void handleCleanTestUsers() {
        String adminEmail = "admin@safelab.com";
        
        // 1. Delete workspace access for non-admin profiles
        entityManager.createNativeQuery(
            "DELETE FROM user_workspace_accesses WHERE user_profile_id IN (SELECT id FROM user_profiles WHERE email != ?1)"
        ).setParameter(1, adminEmail).executeUpdate();

        // 2. Delete user profiles (cascades to lab accesses and notification preferences)
        entityManager.createNativeQuery(
            "DELETE FROM user_profiles WHERE email != ?1"
        ).setParameter(1, adminEmail).executeUpdate();

        // 3. Delete user roles references
        entityManager.createNativeQuery(
            "DELETE FROM user_iam_roles WHERE user_id IN (SELECT id FROM users WHERE email != ?1)"
        ).setParameter(1, adminEmail).executeUpdate();

        // 4. Delete IAM users
        entityManager.createNativeQuery(
            "DELETE FROM users WHERE email != ?1"
        ).setParameter(1, adminEmail).executeUpdate();
        
        logger.info("Successfully cleaned up all test users. Admin account 'admin@safelab.com' was preserved.");
    }
}
