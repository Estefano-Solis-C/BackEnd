package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.test.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.commands.UpdatePasswordCommand;
import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.events.UserRegisteredEvent;
import com.test.backend.iam.domain.model.valueobjects.AuthenticatedUser;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.IamRoleRepository;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final IamRoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(UserRepository userRepository, IamRoleRepository roleRepository,
                                   HashingService hashingService, TokenService tokenService,
                                   ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
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
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(new AuthenticatedUser(user.get(), token));
    }


    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists");
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

        userRepository.save(user);
        eventPublisher.publishEvent(new UserRegisteredEvent(user.getEmail(), user.getFullName()));
        return Optional.of(user);
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
}
