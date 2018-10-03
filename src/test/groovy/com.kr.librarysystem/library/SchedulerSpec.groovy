package com.kr.librarysystem.library

import com.kr.librarysystem.TestMockConfig
import com.kr.librarysystem.email.EmailSender
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import([TestMockConfig])
 class SchedulerSpec {

    @Autowired
    EmailSender emailSender


    def 'Scheduler should be fired 2 times and email sender should be called 2 times'() {
        given:
        //create exiraon for today, sleep

        then:
        2 * emailSender.sendEmail(_, _, _)

    }
}
