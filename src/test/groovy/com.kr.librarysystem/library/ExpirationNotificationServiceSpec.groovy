package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.email.EmailFormatter
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.ExpirationRepository
import spock.lang.Specification

import java.time.LocalDate


class ExpirationNotificationServiceSpec extends Specification {

    private ExpirationRepository repository = Mock()
    private EmailFormatter emailFormatter = Mock()
    def expirationNotificationService = new ExpirationNotificationService(repository, emailFormatter)
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
    def book3 = TestDataG.getBook3()
    def book4 = TestDataG.getBook4()
    def book5 = TestDataG.getBook5()
    def member1 = TestDataG.getLibraryMember1()
    def member2 = TestDataG.getLibraryMember2()
    def member3 = TestDataG.getLibraryMember3()

    def setup() {
        [book1, book2].forEach { book -> book.setBorrowedBy(member1) }
        [book3].forEach { book -> book.setBorrowedBy(member2) }
        [book4, book5].forEach { book -> book.setBorrowedBy(member3) }
    }

    def "NotifyMembersAboutFutureExpirations should call email formatter"() {
        when:
        expirationNotificationService.notifyMembersAboutFutureExpirations([book1, book2, book3, book4, book5])

        then:
        1 * emailFormatter.formatAndSendMailForFuture([book1, book2])
        1 * emailFormatter.formatAndSendMailForFuture([book3])
        1 * emailFormatter.formatAndSendMailForFuture([book4, book5])
    }

    def "NotifyMembersAboutTodayExpirations should call email formatter"() {
        when:
        expirationNotificationService.notifyMembersAboutTodayExpirations([book1, book2, book3, book4, book5])

        then:
        1 * emailFormatter.formatAndSendMailForToday([book1, book2])
        1 * emailFormatter.formatAndSendMailForToday([book3])
        1 * emailFormatter.formatAndSendMailForToday([book4, book5])
    }

    def 'notifyMembers'() {
        given:
        def expirationNotificationService =
                Spy(ExpirationNotificationService, constructorArgs: [repository, emailFormatter ]) // real method code gets executed but listens for method calls
        def expiration = new Expiration(id: 1L, todayExpirations: [book1, book2], futureExpirations: [book3, book4], )
        String today = LocalDate.now().toString()
        repository.findByExpiresOn(today) >> [expiration]

        when:
        expirationNotificationService.notifyMembers()

        then:
        1*expirationNotificationService.notifyMembersAboutTodayExpirations([book1,book2])
        1*expirationNotificationService.notifyMembersAboutFutureExpirations([book3,book4])
        1*repository.delete(expiration)
    }

}
