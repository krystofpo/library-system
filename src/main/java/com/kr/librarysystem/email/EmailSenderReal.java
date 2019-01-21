package com.kr.librarysystem.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod", "local"}) //todo remove profiels?
public class EmailSenderReal implements EmailSender {

    private JavaMailSender sender;
    private String testTo;
    private String from = "library@librarysystem.cz";

    @Autowired
    public EmailSenderReal(JavaMailSender sender, @Value("${spring.mail.to}") String to) {
        this.sender = sender;
        this.testTo = to;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        sender.send(email);
    }

//    void sendTestEmail() {
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setFrom(from);
//        email.setTo(testTo);
//        email.setSubject("library notification test");
//        email.setText("Dobry den, zacalo nabihat zpozdne za nasledujici knihy: ...");
//        sender.send(email);
//    }

}
