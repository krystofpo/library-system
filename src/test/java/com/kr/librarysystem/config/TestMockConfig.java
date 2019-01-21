package com.kr.librarysystem.config;


import com.kr.librarysystem.email.EmailSender;
import com.kr.librarysystem.email.EmailSenderReal;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import spock.mock.DetachedMockFactory;

@TestConfiguration
@ComponentScan("com.kr.librarysystem")
public class TestMockConfig {
    private DetachedMockFactory factory = new DetachedMockFactory();

    @Bean
    public EmailSender emailSender(){
      return factory.Mock(EmailSenderReal.class);
    }
}
