package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.TestMockConfig
import com.kr.librarysystem.email.EmailSender
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.ExpirationRepository
import org.spockframework.compiler.model.Spec
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
 class SchedulerIntegrationSpec extends Specification{

    @Autowired
    ExpirationRepository expirationRepository


    def 'Scheduler should be fired and email sender should be called 2 times'() {
        given:
        //create exiraon for today, sleep
        String today = LocalDate.now().toString()

        def book1 = TestDataG.book1
        TestDataG.persistBooksAndNestedObjects([book1], em)
        def expiration = new Expiration()
        expiration.setExpiresOn(today)
        expiration.addToTodayExpirations(book1)
        expirationRepository.save(expiration)

        then:
        2 * emailSender.sendEmail(_, _, _)

    }
}
