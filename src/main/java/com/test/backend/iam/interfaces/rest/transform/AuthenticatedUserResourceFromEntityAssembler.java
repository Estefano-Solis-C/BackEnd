package com.test.backend.iam.interfaces.rest.transform;

import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.valueobjects.AuthenticatedUser;
import com.test.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.util.List;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(AuthenticatedUser entity) {
        List<String> roles = entity.user().getRoles() != null
                ? entity.user().getRoles().stream().map(Role::getStringName).toList()
                : List.of();

        return new AuthenticatedUserResource(
                entity.user().getId(),
                entity.user().getEmail(),
                entity.token(),
                roles
        );
    }
}
