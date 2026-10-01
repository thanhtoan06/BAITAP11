package com.laptrinhweb.util;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public final class EmailUtil_24162129 {

    private static final String FROM_EMAIL = "llthanhtoan@gmail.com";
    private static final String APP_PASSWORD = "hawkgillbxvgbnpr";

    private EmailUtil_24162129() {
    }

    public static boolean sendEmail(String toEmail, String subject, String body) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setContent(body, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String getRandomOTP() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000));
    }
}