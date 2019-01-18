package com.kr.librarysystem;


import com.kr.librarysystem.email.EmailSender;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import spock.mock.DetachedMockFactory;

//@TestConfiguration
public class TestMockConfig {
    private DetachedMockFactory factory = new DetachedMockFactory();


    //@Bean
    public EmailSender emailSender(){
      return factory.Mock(EmailSender.class);
    }
}
