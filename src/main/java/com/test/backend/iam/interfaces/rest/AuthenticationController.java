package com.test.backend.iam.interfaces.rest;

import com.test.backend.iam.domain.model.commands.ConfirmAccountCommand;
import com.test.backend.iam.domain.model.commands.ResendConfirmationCodeCommand;
import com.test.backend.iam.domain.model.commands.UpdatePasswordCommand;
import com.test.backend.iam.domain.model.exceptions.UserNotConfirmedException;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.interfaces.rest.resources.*;
import com.test.backend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign In", description = "Authenticates a user and returns a token")
    public ResponseEntity<?> signIn(@RequestBody SignInResource signInResource) {
        try {
            var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
            var authenticatedUser = userCommandService.handle(signInCommand);
            if (authenticatedUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get());
            return ResponseEntity.ok(authenticatedUserResource);
        } catch (UserNotConfirmedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "ACCOUNT_NOT_CONFIRMED", "email", signInResource.email()));
        }
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign Up", description = "Registers a new user in the platform")
    public ResponseEntity<?> signUp(@RequestBody SignUpResource signUpResource) {
        try {
            var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
            var user = userCommandService.handle(signUpCommand);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/confirm-account")
    @Operation(summary = "Confirm Account", description = "Confirms a user account using the verification code sent by email")
    public ResponseEntity<Void> confirmAccount(@RequestBody ConfirmAccountResource confirmAccountResource) {
        var command = new ConfirmAccountCommand(confirmAccountResource.email(), confirmAccountResource.code());
        var result = userCommandService.handle(command);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resend-code")
    @Operation(summary = "Resend Code", description = "Resends the account verification code via email")
    public ResponseEntity<Void> resendCode(@RequestBody ResendCodeResource resendCodeResource) {
        var command = new ResendConfirmationCodeCommand(resendCodeResource.email());
        var result = userCommandService.handle(command);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clean-test-users")
    @Operation(summary = "Clean Test Users", description = "Deletes all registered test users and their profiles, keeping only the default administrator.")
    public ResponseEntity<Void> cleanTestUsers() {
        userCommandService.handleCleanTestUsers();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    @Operation(summary = "Update Password", description = "Updates the password of the currently authenticated user")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordResource resource) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var command = new UpdatePasswordCommand(email, resource.currentPassword(), resource.newPassword());
        var result = userCommandService.handle(command);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
