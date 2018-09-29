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
    private String to;

    @Autowired
    public EmailSender(JavaMailSender sender, @Value("${spring.mail.to}") String to) {
        this.sender = sender;
        this.to = to;
    }


    public void sendEmail(String to, String from, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("library@librarysystem.cz");
        email.setTo(this.to);
        email.setSubject("upozorneni na zpozdne");
        email.setText("nabiha zpozdne za tyto jednotky: Neruda, ");
        sender.send(email);
    }
}
