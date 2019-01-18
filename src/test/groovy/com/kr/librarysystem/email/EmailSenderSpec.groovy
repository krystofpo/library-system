package com.kr.librarysystem.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class EmailSenderSpec extends Specification {

    //@Autowired
    //private EmailSenderReal sender

//    def "SendEmail"() {
////TODO
//        /*
//        to run the test correctly you need to set JVM arguments
//        -Dspring.mail.username=xxxx -Dspring.mail.password=xxxx -Dspring.mail.to=xxxx
//        if you don't set them then nonsense values from application.properties will be used
//         */
//        expect:
//        sender.sendTestEmail()
//        true
//    }
}
