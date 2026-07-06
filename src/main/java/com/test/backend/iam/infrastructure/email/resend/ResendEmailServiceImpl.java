package com.test.backend.iam.infrastructure.email.resend;

import com.resend.*;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.test.backend.iam.application.internal.outboundservices.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(ResendEmailServiceImpl.class);

    private final Resend resend;
    private final String apiKey;

    public ResendEmailServiceImpl(@Value("${resend.api-key}") String apiKey) {
        this.apiKey = apiKey;
        if (apiKey == null || apiKey.trim().isEmpty()) {
            logger.error("RESEND CONFIG ERROR: API Key is null or empty!");
        } else {
            logger.info("Resend API Key loaded successfully. Value: {}...", apiKey.substring(0, Math.min(apiKey.length(), 10)));
        }
        this.resend = new Resend(apiKey);
    }

    @Override
    @Async
    public void sendVerificationEmail(String toEmail, String fullName, String verificationCode) {
        logger.info("Starting async email delivery task to {}...", toEmail);
        CreateEmailOptions sendEmailRequest = CreateEmailOptions.builder()
                .from("onboarding@resend.dev") // Standard onboarding test sender
                .to(toEmail)
                .subject("Confirm your SafeLab Account")
                .html("<div style=\"font-family: sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 12px; background-color: #ffffff;\">" +
                        "  <h2 style=\"color: #0f172a; margin-bottom: 8px;\">Welcome to SafeLab</h2>" +
                        "  <p style=\"color: #475569; font-size: 16px;\">Hi " + fullName + ",</p>" +
                        "  <p style=\"color: #475569; font-size: 16px; line-height: 24px;\">Thank you for registering. Please confirm your account by entering the verification code below in the application:</p>" +
                        "  <div style=\"text-align: center; margin: 32px 0;\">" +
                        "    <span style=\"display: inline-block; padding: 12px 32px; background-color: #f1f5f9; border-radius: 8px; font-size: 32px; font-weight: 700; color: #3b82f6; letter-spacing: 6px;\">" + verificationCode + "</span>" +
                        "  </div>" +
                        "  <p style=\"color: #64748b; font-size: 14px;\">This code is valid for 15 minutes. If you did not request this, you can safely ignore this email.</p>" +
                        "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin: 24px 0;\" />" +
                        "  <p style=\"color: #94a3b8; font-size: 12px; text-align: center;\">&copy; 2026 SafeLab. All rights reserved.</p>" +
                        "</div>")
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(sendEmailRequest);
            logger.info("Verification email sent to {} successfully. Response ID: {}", toEmail, response.getId());
        } catch (Exception e) {
            logger.error("Failed to send verification email to {} via Resend", toEmail, e);
            throw new RuntimeException("Email delivery failed: " + e.getMessage(), e);
        }
    }
}
