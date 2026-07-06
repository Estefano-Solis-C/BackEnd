package com.test.backend.iam.application.internal.outboundservices.email;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String fullName, String verificationCode);
}
