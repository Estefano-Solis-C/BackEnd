package com.test.backend.iam.interfaces.rest;

import com.test.backend.iam.domain.model.commands.ConfirmAccountCommand;
import com.test.backend.iam.domain.model.commands.ResendConfirmationCodeCommand;
import com.test.backend.iam.domain.model.commands.UpdatePasswordCommand;
import com.test.backend.iam.domain.model.exceptions.UserAlreadyExistsException;
import com.test.backend.iam.domain.model.exceptions.UserNotConfirmedException;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.interfaces.rest.resources.*;
import com.test.backend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign In", description = "Authenticates a user and returns a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credentials verified successfully, returning the cryptographic JWT token signed with HMAC-SHA256",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticatedUserResource.class))),
            @ApiResponse(responseCode = "400", description = "Required fields missing or invalid institutional email syntax", content = @Content),
            @ApiResponse(responseCode = "401", description = "Incorrect password or unconfirmed account in email service", content = @Content)
    })
    public ResponseEntity<?> signIn(@RequestBody SignInResource signInResource) {
        if (signInResource == null || signInResource.email() == null || signInResource.email().isBlank()
                || signInResource.password() == null || signInResource.password().isBlank()
                || !isValidEmail(signInResource.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Required fields missing or invalid institutional email syntax"));
        }

        try {
            var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
            var authenticatedUser = userCommandService.handle(signInCommand);
            if (authenticatedUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Incorrect password or unconfirmed account in email service"));
            }
            var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get());
            return ResponseEntity.ok(authenticatedUserResource);
        } catch (UserNotConfirmedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Incorrect password or unconfirmed account in email service", "email", signInResource.email()));
        }
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign Up", description = "Registers a new user in the platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Required fields missing or invalid institutional email syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Attempt to register with an already existing username or email in database", content = @Content)
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpResource signUpResource) {
        if (signUpResource == null || signUpResource.email() == null || signUpResource.email().isBlank()
                || signUpResource.password() == null || signUpResource.password().isBlank()
                || signUpResource.fullName() == null || signUpResource.fullName().isBlank()
                || !isValidEmail(signUpResource.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Required fields missing or invalid institutional email syntax"));
        }

        try {
            var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
            var user = userCommandService.handle(signUpCommand);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Attempt to register with an already existing username or email in database"));
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
