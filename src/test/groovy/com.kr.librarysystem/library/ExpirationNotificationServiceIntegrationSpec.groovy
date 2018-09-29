package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.email.EmailFormatter
import com.kr.librarysystem.email.EmailSender
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.ExpirationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager
import java.text.SimpleDateFormat


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ExpirationNotificationServiceIntegrationSpec extends Specification {

    @Autowired
    private EntityManager em

    @Autowired
    private ExpirationRepository repository

    private EmailSender emailSender
    private EmailFormatter emailFormatter
    private ExpirationNotificationService service

    def setup() {
        emailSender = Mock()
        emailFormatter = new EmailFormatter(emailSender)
        service = new ExpirationNotificationService(repository, emailFormatter)
    }

    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
    def book3 = TestDataG.getBook3()
    def book4 = TestDataG.getBook4()
    def member1 = TestDataG.getLibraryMember1()
    def member2 = TestDataG.getLibraryMember2()

    def "NotifyMembers"() {
        given:
        member1.setEmail('email1')
        member2.setEmail('email2')
        book1.setBorrowedBy(member1)
        book2.setBorrowedBy(member2)
        book3.setBorrowedBy(member2)
        book4.setBorrowedBy(member2)
        String today = new SimpleDateFormat('yyyy-MM-dd').format(new Date())
        def expires = new Expiration(expiresOn: today, todayExpirations: [book1, book2, book3], futureExpirations: [book4])
        [book1, book2, book3, book4].each {
            em.persist(it.getAuthor())
            em.persist(it)
            em.persist(it.getBorrowedBy())
            it.setBorrowedUntil(new Date())
        }
        em.persist(expires)

        when:
        service.notifyMembers()

        then:
        1 * emailSender.sendEmail('email1', { it.contains('dnes') }, { it.contains('Neruda') })
        1 * emailSender.sendEmail('email2', { it.contains('dnes') }, { it.contains('Valka') && it.contains('Saman') })
        1 * emailSender.sendEmail('email2', { it.contains('brzy') }, { it.contains('1984') })
    }
}
