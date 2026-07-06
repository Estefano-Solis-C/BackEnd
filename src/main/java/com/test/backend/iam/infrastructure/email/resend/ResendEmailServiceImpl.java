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

    @Override
    @Async
    public void sendAlertEmail(String toEmail, String recipientName, String labName, String alertTitle, String alertDescription, String severity, String sensorName, String metricValue, String thresholdValue) {
        logger.info("Starting async alert email delivery task to {} for lab {}...", toEmail, labName);
        
        String severityColor = "CRITICAL".equalsIgnoreCase(severity) ? "#ef4444" : "#f59e0b";
        String severityLabel = "CRITICAL".equalsIgnoreCase(severity) ? "CRITICAL INCIDENT" : "WARNING ALERT";
        
        CreateEmailOptions sendEmailRequest = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(toEmail)
                .subject("[" + severityLabel + "] " + alertTitle + " in " + labName)
                .html("<div style=\"font-family: sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 12px; background-color: #ffffff;\">" +
                        "  <div style=\"display: flex; align-items: center; justify-content: space-between; margin-bottom: 24px;\">" +
                        "    <span style=\"font-size: 20px; font-weight: 700; color: #0f172a;\">SafeLab Telemetry</span>" +
                        "    <span style=\"display: inline-block; padding: 6px 12px; font-size: 12px; font-weight: 700; border-radius: 9999px; background-color: " + severityColor + "15; color: " + severityColor + "; text-transform: uppercase;\">" + severityLabel + "</span>" +
                        "  </div>" +
                        "  <h2 style=\"color: #0f172a; margin-top: 0; margin-bottom: 12px;\">" + alertTitle + "</h2>" +
                        "  <p style=\"color: #475569; font-size: 16px; line-height: 24px; margin-bottom: 24px;\">Hi " + recipientName + ",</p>" +
                        "  <p style=\"color: #475569; font-size: 16px; line-height: 24px; margin-bottom: 24px;\">An alert condition has been detected in laboratory <strong>" + labName + "</strong>:</p>" +
                        "  <div style=\"background-color: #f8fafc; border-left: 4px solid " + severityColor + "; padding: 16px; border-radius: 0 8px 8px 0; margin-bottom: 24px;\">" +
                        "    <p style=\"margin: 0 0 8px 0; color: #334155; font-size: 15px;\"><strong>Description:</strong> " + alertDescription + "</p>" +
                        "    <p style=\"margin: 0 0 8px 0; color: #334155; font-size: 15px;\"><strong>Sensor:</strong> " + sensorName + "</p>" +
                        "    <p style=\"margin: 0 0 8px 0; color: #334155; font-size: 15px;\"><strong>Current Value:</strong> <span style=\"color: " + severityColor + "; font-weight: 600;\">" + metricValue + "</span></p>" +
                        "    <p style=\"margin: 0; color: #334155; font-size: 15px;\"><strong>Threshold Limit:</strong> " + thresholdValue + "</p>" +
                        "  </div>" +
                        "  <p style=\"color: #64748b; font-size: 14px; line-height: 20px;\">You are receiving this notification because email alerts are enabled in the settings for <strong>" + labName + "</strong>. You can update these settings anytime in the SafeLab portal.</p>" +
                        "  <hr style=\"border: none; border-top: 1px solid #f1f5f9; margin: 24px 0;\" />" +
                        "  <p style=\"color: #94a3b8; font-size: 12px; text-align: center; margin: 0;\">&copy; 2026 SafeLab. All rights reserved.</p>" +
                        "</div>")
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(sendEmailRequest);
            logger.info("Alert notification email sent to {} successfully. Response ID: {}", toEmail, response.getId());
        } catch (Exception e) {
            logger.error("Failed to send alert notification email to {} via Resend", toEmail, e);
        }
    }
}
