package com.kr.librarysystem.email;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private JavaMailSender sender;
    private String testTo;
    private String from = "library@librarysystem.cz";

    @Autowired
    public EmailSender(JavaMailSender sender, @Value("${spring.mail.to}") String to) {
        this.sender = sender;
        this.testTo = to;
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        sender.send(email);
    }

    void sendTestEmail() {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(testTo);
        email.setSubject("library notification test");
        email.setText("Dobry den, zacalo nabihat zpozdne za nasledujici knihy: ...");
        sender.send(email);
    }

}
