package com.resturent.resturen_spring.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReservationConfirmation(String to, String customerName, LocalDateTime startTime, LocalDateTime endTime, int tableNumber, int numberOfPeople) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reservation Confirmation");
        message.setText(String.format("Dear %s,\n\nYour reservation has been confirmed.\n\nDetails:\nStart Time: %s\nEnd Time: %s\nTable Number: %d\nNumber of People: %d\n\nThank you for choosing our restaurant!",
                customerName, startTime, endTime, tableNumber, numberOfPeople));
        mailSender.send(message);
    }
}
