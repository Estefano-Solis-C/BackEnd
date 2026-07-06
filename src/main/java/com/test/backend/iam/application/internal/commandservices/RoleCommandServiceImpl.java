package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.domain.model.commands.SeedRolesCommand;
import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.domain.services.RoleCommandService;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.IamRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final IamRoleRepository roleRepository;

    public RoleCommandServiceImpl(IamRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(role));
            }
        });
    }
}
