package com.kr.librarysystem.email;

public interface EmailSender {

    void sendEmail(String to, String subject, String message);
}
