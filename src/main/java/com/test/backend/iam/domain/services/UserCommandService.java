package com.test.backend.iam.domain.services;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.ConfirmAccountCommand;
import com.test.backend.iam.domain.model.commands.ResendConfirmationCodeCommand;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.commands.UpdatePasswordCommand;
import com.test.backend.iam.domain.model.valueobjects.AuthenticatedUser;

import java.util.Optional;

public interface UserCommandService {
    Optional<AuthenticatedUser> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    boolean handle(UpdatePasswordCommand command);
    boolean handle(ConfirmAccountCommand command);
    boolean handle(ResendConfirmationCodeCommand command);
    void handleCleanTestUsers();
}
