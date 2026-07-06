package com.test.backend.iam.domain.model.commands;

public record ConfirmAccountCommand(String email, String code) {
}
