package com.test.backend.iam.domain.model.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
        super("Attempt to register with an already existing username or email in database");
    }
}
