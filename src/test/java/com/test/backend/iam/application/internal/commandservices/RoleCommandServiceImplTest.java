package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.domain.model.commands.SeedRolesCommand;
import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.IamRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleCommandServiceImplTest {

    @Mock
    private IamRoleRepository roleRepository;

    @InjectMocks
    private RoleCommandServiceImpl service;

    @Test
    @DisplayName("handle(SeedRolesCommand) should save all non-existing roles (AAA)")
    void handle_SeedRoles_ShouldSaveMissingRoles_WhenRolesDoNotExist() {
        // Arrange
        var command = new SeedRolesCommand();
        when(roleRepository.existsByName(any(Roles.class))).thenReturn(false);

        // Act
        service.handle(command);

        // Assert
        verify(roleRepository, times(Roles.values().length)).save(any(Role.class));
    }

    @Test
    @DisplayName("handle(SeedRolesCommand) should not save roles that already exist (AAA)")
    void handle_SeedRoles_ShouldNotSaveRoles_WhenRolesAlreadyExist() {
        // Arrange
        var command = new SeedRolesCommand();
        when(roleRepository.existsByName(any(Roles.class))).thenReturn(true);

        // Act
        service.handle(command);

        // Assert
        verify(roleRepository, never()).save(any(Role.class));
    }
}
