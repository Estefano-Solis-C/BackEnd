package com.test.backend.alerts.infrastructure.security;

import com.test.backend.shared.application.CurrentWorkspaceService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JWTSecurityContextAdapter {

    private final CurrentWorkspaceService currentWorkspaceService;

    public JWTSecurityContextAdapter(CurrentWorkspaceService currentWorkspaceService) {
        this.currentWorkspaceService = currentWorkspaceService;
    }

    public String getCurrentActor() {
        return currentWorkspaceService.getCurrentUserEmail().orElse("system-automation");
    }

    public Optional<Long> getCurrentWorkspaceId() {
        return currentWorkspaceService.getCurrentWorkspaceId();
    }

    public boolean isCurrentUserAdmin() {
        return currentWorkspaceService.isCurrentUserAdmin();
    }
}
