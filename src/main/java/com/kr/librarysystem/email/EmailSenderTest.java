package com.kr.librarysystem.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class EmailSenderTest implements EmailSender{


    @Override
    public void sendEmail(String to, String subject, String message) {
        //do nothing
    }
}
