package com.schedulerates.user.service.impl;

import com.schedulerates.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from:noreply@booking-container.com}")
    private String fromEmail;

    @Value("${app.frontend.url:https://192.168.1.202:7000}")
    private String frontendUrl;

    @Value("${app.email.activation-subject:Activate Your Booking Container Account}")
    private String activationSubject;

    @Override
    public void sendActivationEmail(String to, String activationToken, String userName) {
        try {
            String activationUrl = frontendUrl + "/activate-account?token=" + activationToken;
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(activationSubject);
            helper.setText(buildHtmlEmailContent(userName, activationUrl), true); // true = isHtml
            
            mailSender.send(message);
            log.info("Activation email sent to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send activation email to: {}", to, e);
            // Fallback to plain text email
            sendPlainTextEmail(to, activationToken, userName);
        }
    }

    private String buildHtmlEmailContent(String userName, String activationUrl) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        line-height: 1.6; 
                        color: #333; 
                        margin: 0; 
                        padding: 20px; 
                        background: #f4f4f4; 
                    }
                    .container { 
                        max-width: 600px; 
                        margin: 0 auto; 
                        background: white; 
                        border-radius: 8px; 
                        overflow: hidden; 
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1); 
                    }
                    .header { 
                        background: #4CAF50; 
                        color: white; 
                        padding: 30px; 
                        text-align: center; 
                    }
                    .content { 
                        padding: 30px; 
                    }
                    .button { 
                        display: inline-block; 
                        padding: 14px 28px; 
                        background: #4CAF50; 
                        color: white; 
                        text-decoration: none; 
                        border-radius: 5px; 
                        font-size: 16px; 
                        font-weight: bold; 
                        margin: 20px 0; 
                    }
                    .footer { 
                        background: #f8f9fa; 
                        padding: 20px; 
                        text-align: center; 
                        color: #666; 
                        font-size: 12px; 
                    }
                    .code { 
                        background: #f8f9fa; 
                        padding: 15px; 
                        border-radius: 5px; 
                        font-family: monospace; 
                        word-break: break-all; 
                        margin: 15px 0; 
                        border-left: 4px solid #4CAF50; 
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Welcome to Booking Container!</h1>
                    </div>
                    <div class="content">
                        <p>Hello <strong>%s</strong>,</p>
                        <p>Thank you for registering with Booking Container. To complete your registration and activate your account, please click the button below:</p>
                        <p style="text-align: center;">
                            <a href="%s" class="button">Activate Your Account</a>
                        </p>
                        <p>If the button doesn't work, copy and paste this link into your browser:</p>
                        <div class="code">%s</div>
                        <p><strong>Important:</strong> This activation link will expire in 24 hours.</p>
                        <p>If you didn't create an account with Booking Container, please ignore this email.</p>
                        <p>Best regards,<br><strong>The Booking Container Team</strong></p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Booking Container. All rights reserved.</p>
                        <p>This is an automated message, please do not reply to this email.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, activationUrl, activationUrl);
    }

    // Fallback method for plain text email
    private void sendPlainTextEmail(String to, String activationToken, String userName) {
        try {
            String activationUrl = frontendUrl + "/activate-account?token=" + activationToken;
            
            String plainTextContent = """
                Hello %s,

                Thank you for registering with Booking Container!

                To activate your account, please visit this link:
                %s

                This activation link will expire in 24 hours.

                If you didn't create an account with Booking Container, please ignore this email.

                Best regards,
                The Booking Container Team
                """.formatted(userName, activationUrl);

            // Create simple email as fallback
            jakarta.mail.internet.MimeMessage fallbackMessage = mailSender.createMimeMessage();
            MimeMessageHelper fallbackHelper = new MimeMessageHelper(fallbackMessage, "UTF-8");
            fallbackHelper.setFrom(fromEmail);
            fallbackHelper.setTo(to);
            fallbackHelper.setSubject(activationSubject);
            fallbackHelper.setText(plainTextContent);
            
            mailSender.send(fallbackMessage);
            log.info("Plain text activation email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send plain text email to: {}", to, e);
        }
    }
}