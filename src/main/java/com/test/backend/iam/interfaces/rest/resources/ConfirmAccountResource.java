package com.test.backend.iam.interfaces.rest.resources;

public record ConfirmAccountResource(String email, String code) {
}
