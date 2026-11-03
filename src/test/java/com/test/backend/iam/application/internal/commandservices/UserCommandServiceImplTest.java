package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.application.internal.outboundservices.email.EmailService;
import com.test.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.test.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.ConfirmAccountCommand;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.entities.Role;
import com.test.backend.iam.domain.model.exceptions.UserAlreadyExistsException;
import com.test.backend.iam.domain.model.exceptions.UserNotConfirmedException;
import com.test.backend.iam.domain.model.valueobjects.AuthenticatedUser;
import com.test.backend.iam.domain.model.valueobjects.Roles;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.IamRoleRepository;
import com.test.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IamRoleRepository roleRepository;

    @Mock
    private HashingService hashingService;

    @Mock
    private TokenService tokenService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private EmailService emailService;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserCommandServiceImpl service;

    @Test
    @DisplayName("handle(SignInCommand) should return AuthenticatedUser when credentials are valid and account is confirmed (AAA)")
    void handle_SignIn_ShouldReturnAuthenticatedUser_WhenValidCredentialsAndConfirmed() {
        // Arrange
        var command = new SignInCommand("operator@safelab.pe", "Password123!");
        var user = mock(User.class);
        when(userRepository.findByEmail("operator@safelab.pe")).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn("encodedPassword");
        when(hashingService.matches("Password123!", "encodedPassword")).thenReturn(true);
        when(user.isConfirmed()).thenReturn(true);
        when(user.getEmail()).thenReturn("operator@safelab.pe");
        when(tokenService.generateToken("operator@safelab.pe")).thenReturn("jwt-token-123");

        // Act
        Optional<AuthenticatedUser> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("jwt-token-123", result.get().token());
        assertSame(user, result.get().user());
        verify(userRepository).findByEmail("operator@safelab.pe");
        verify(hashingService).matches("Password123!", "encodedPassword");
        verify(tokenService).generateToken("operator@safelab.pe");
    }

    @Test
    @DisplayName("handle(SignInCommand) should return empty when user does not exist (AAA)")
    void handle_SignIn_ShouldReturnEmpty_WhenUserNotFound() {
        // Arrange
        var command = new SignInCommand("unknown@safelab.pe", "Password123!");
        when(userRepository.findByEmail("unknown@safelab.pe")).thenReturn(Optional.empty());

        // Act
        Optional<AuthenticatedUser> result = service.handle(command);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail("unknown@safelab.pe");
        verifyNoInteractions(hashingService, tokenService);
    }

    @Test
    @DisplayName("handle(SignInCommand) should return empty when password does not match (AAA)")
    void handle_SignIn_ShouldReturnEmpty_WhenPasswordInvalid() {
        // Arrange
        var command = new SignInCommand("operator@safelab.pe", "WrongPassword!");
        var user = mock(User.class);
        when(userRepository.findByEmail("operator@safelab.pe")).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn("encodedPassword");
        when(hashingService.matches("WrongPassword!", "encodedPassword")).thenReturn(false);

        // Act
        Optional<AuthenticatedUser> result = service.handle(command);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail("operator@safelab.pe");
        verify(hashingService).matches("WrongPassword!", "encodedPassword");
        verifyNoInteractions(tokenService);
    }

    @Test
    @DisplayName("handle(SignInCommand) should throw UserNotConfirmedException when account is unconfirmed (AAA)")
    void handle_SignIn_ShouldThrowException_WhenAccountNotConfirmed() {
        // Arrange
        var command = new SignInCommand("unconfirmed@safelab.pe", "Password123!");
        var user = mock(User.class);
        when(userRepository.findByEmail("unconfirmed@safelab.pe")).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn("encodedPassword");
        when(hashingService.matches("Password123!", "encodedPassword")).thenReturn(true);
        when(user.isConfirmed()).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotConfirmedException.class, () -> service.handle(command));
        verify(userRepository).findByEmail("unconfirmed@safelab.pe");
        verify(hashingService).matches("Password123!", "encodedPassword");
        verifyNoInteractions(tokenService);
    }

    @Test
    @DisplayName("handle(SignUpCommand) should create user and send confirmation email when email is new (AAA)")
    void handle_SignUp_ShouldCreateUser_WhenEmailDoesNotExist() {
        // Arrange
        var command = new SignUpCommand(
                "newuser@safelab.pe",
                "Password123!",
                "New User",
                List.of(Roles.ROLE_USER)
        );
        when(userRepository.existsByEmail("newuser@safelab.pe")).thenReturn(false);
        var role = new Role(Roles.ROLE_USER);
        when(roleRepository.findByName(Roles.ROLE_USER)).thenReturn(Optional.of(role));
        when(hashingService.encode("Password123!")).thenReturn("encodedPassword");

        // Act
        Optional<User> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("newuser@safelab.pe", result.get().getEmail());
        verify(userRepository).existsByEmail("newuser@safelab.pe");
        verify(userRepository).save(any(User.class));
        verify(emailService).sendVerificationEmail(eq("newuser@safelab.pe"), eq("New User"), anyString());
        verify(eventPublisher).publishEvent(any(com.test.backend.iam.domain.model.events.UserRegisteredEvent.class));
    }

    @Test
    @DisplayName("handle(SignUpCommand) should throw UserAlreadyExistsException when email already exists (AAA)")
    void handle_SignUp_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        var command = new SignUpCommand(
                "existing@safelab.pe",
                "Password123!",
                "Existing User",
                List.of(Roles.ROLE_USER)
        );
        when(userRepository.existsByEmail("existing@safelab.pe")).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> service.handle(command));
        verify(userRepository).existsByEmail("existing@safelab.pe");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(emailService, hashingService);
    }

    @Test
    @DisplayName("handle(ConfirmAccountCommand) should return true when code matches (AAA)")
    void handle_ConfirmAccount_ShouldReturnTrue_WhenCodeMatches() {
        // Arrange
        var command = new ConfirmAccountCommand("user@safelab.pe", "123456");
        var user = new User("user@safelab.pe", "pwd", "User Name");
        user.setConfirmed(false);
        user.setConfirmationCode("123456");
        when(userRepository.findByEmail("user@safelab.pe")).thenReturn(Optional.of(user));

        // Act
        boolean result = service.handle(command);

        // Assert
        assertTrue(result);
        assertTrue(user.isConfirmed());
        assertNull(user.getConfirmationCode());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("handle(ConfirmAccountCommand) should return false when code does not match (AAA)")
    void handle_ConfirmAccount_ShouldReturnFalse_WhenCodeDoesNotMatch() {
        // Arrange
        var command = new ConfirmAccountCommand("user@safelab.pe", "999999");
        var user = new User("user@safelab.pe", "pwd", "User Name");
        user.setConfirmed(false);
        user.setConfirmationCode("123456");
        when(userRepository.findByEmail("user@safelab.pe")).thenReturn(Optional.of(user));

        // Act
        boolean result = service.handle(command);

        // Assert
        assertFalse(result);
        assertFalse(user.isConfirmed());
        verify(userRepository, never()).save(any());
    }
}
