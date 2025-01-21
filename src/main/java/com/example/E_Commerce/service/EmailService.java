package com.example.E_Commerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVefificationOTPEmail(String userEmail, String otp, String Subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setSubject(Subject);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setTo(userEmail);
            mailSender.send(mimeMessage);

        } catch (MailException e) {
            throw new MailSendException("Failed to send email");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
