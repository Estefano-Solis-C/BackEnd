package com.test.backend.iam.application.internal.outboundservices.email;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String fullName, String verificationCode);
    void sendAlertEmail(String toEmail, String recipientName, String labName, String alertTitle, String alertDescription, String severity, String sensorName, String metricValue, String thresholdValue);
}
