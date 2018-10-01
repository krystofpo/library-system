package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.AuthorRepository
import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.ExpirationRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository
import com.kr.librarysystem.utils.DateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ExpirationServiceIntegrationSpec extends Specification {

    @Autowired
    ExpirationRepository expirationRepository

    @Autowired
    EntityManager em

    @Autowired
    BookRepository bookRepository

    @Autowired
    AuthorRepository authorRepository

    @Autowired
    LibraryMemberRepository libraryMemberRepository

    DateService dateService = Mock()

    ExpirationService service
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
   def book3 = TestDataG.getBook3()
    private String date1
    private String date2
    private String date3
    Closure<Boolean> assertEqualsExpirations

    def setup() {
        service = new ExpirationService(expirationRepository, dateService)
        authorRepository.deleteAll()
        libraryMemberRepository.deleteAll()
        bookRepository.deleteAll()
        expirationRepository.deleteAll()

        dateService.todayLocalDate >> LocalDate.parse('2018-08-01', DateTimeFormatter.ISO_LOCAL_DATE)

        book1.setNotificationPeriod(Period.ofDays(1))   //date1 future
        book1.setBorrowingPeriod(Period.ofDays(2))      //date2 today
        book2.setNotificationPeriod(Period.ofDays(2))   //date2 future
        book2.setBorrowingPeriod(Period.ofDays(5))      //date3 today
        date1 = '2018-08-02'
        date2 = '2018-08-03'
        date3 = '2018-08-06'

        TestDataG.persistBooksAndNestedObjects([book1, book2, book3], em)


        assertEqualsExpirations = { Expiration actual, Expiration expected ->
            actual.getExpiresOn().equals(expected.getExpiresOn()) &&
                    actual.getTodayExpirations().containsAll(expected.getTodayExpirations()) &&
                    expected.getTodayExpirations().containsAll(actual.getTodayExpirations()) &&
                    actual.getFutureExpirations().containsAll(expected.getFutureExpirations()) &&
                    expected.getFutureExpirations().containsAll(actual.getFutureExpirations())
        }

//        def expiration1 = new Expiration(expiresOn: date2, )
//        def expectedExpiration1
//        //todo
//
//
//        def matchExpiration1 = { Expiration e = it
//            e.getExpiresOn()== date2 &&
//                    e.getFutureExpirations().size()==2 &&
//                    e.getFutureExpirations().containsAll([borrowedBook, book1])
//            e.getTodayExpirations()==[book2]}
//
//        def matchExpiration2 = { Expiration e = it
//            e.getExpiresOn()=='2018-08-02' &&
//                    e.getFutureExpirations()==[book2] &&
//                    e.getTodayExpirations()==[]}
//
//
//        def matchExpiration3 = { Expiration e = it
//            e.getExpiresOn()== date3 &&
//                    e.getFutureExpirations()==[borrowedBook] &&
//                    e.getTodayExpirations()==[book1]}
//
//        expirationRepository.save(new Expiration(futureExpirations: [borrowedBook], expiresOn: date2))
//        expirationRepository.save(new Expiration(futureExpirations: [borrowedBook], expiresOn: date3))
//
//

    }

    def "AddExpiration creates new expirations"() {
        given:
        def expectedExpiration1 = new Expiration(expiresOn: date1, todayExpirations: [], futureExpirations: [book1])
        def expectedExpiration2 = new Expiration(expiresOn: date2, todayExpirations: [book1], futureExpirations: [book2])
        def expectedExpiration3 = new Expiration(expiresOn: date3, todayExpirations: [book2], futureExpirations: [])


        when:
        service.addExpiration([book1, book2])

        then:
        expirationRepository.count() == 3L
        def expirations = expirationRepository.findAll()
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration1) }
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration2) }
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration3) }

    }

    def 'addExpiration finds existing Expiration and adds to future expirations'() {
        given:
        expirationRepository.save(new Expiration(expiresOn: date1, todayExpirations: [], futureExpirations: [book2]))
        def expectedExpiration1 = new Expiration(expiresOn: date1, todayExpirations: [], futureExpirations: [book2, book1])
        def expectedExpiration2 = new Expiration(expiresOn: date2, todayExpirations: [book1], futureExpirations: [])

        when:
        service.addExpiration([book1])

        then:
        expirationRepository.count() == 2L
        def expirations = expirationRepository.findAll()
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration1) }
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration2) }

    }

    def 'addExpiration finds existing Expiration and adds to today expirations'() {
        given:
        expirationRepository.save(new Expiration(expiresOn: date2, todayExpirations: [book2], futureExpirations: []))
        def expectedExpiration2 = new Expiration(expiresOn: date2, todayExpirations: [book1, book2], futureExpirations: [])
        def expectedExpiration1 = new Expiration(expiresOn: date1, todayExpirations: [], futureExpirations: [book1])

        when:
        service.addExpiration([book1])

        then:
        expirationRepository.count() == 2L
        def expirations = expirationRepository.findAll()
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration1) }
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration2) }

    }

    def "RemoveExpiration removes books from existing expirations - today books"() {
        given:
        expirationRepository.save(new Expiration(expiresOn: date2, todayExpirations: [book1, book2,book3], futureExpirations: []))
        def expectedExpiration = new Expiration(expiresOn: date2, todayExpirations: [book2], futureExpirations: [])

when:
service.removeExpiration([book1, book3])

        then:
        expirationRepository.count() == 1L
        def expirations = expirationRepository.findAll()
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration) }
    }

    def "RemoveExpiration removes books from existing expirations - future books"() {
        given:
        expirationRepository.save(new Expiration(expiresOn: date2, todayExpirations: [book2], futureExpirations: [book1]))
        def expectedExpiration = new Expiration(expiresOn: date2, todayExpirations: [book2], futureExpirations: [])

        when:
        service.removeExpiration([book1])

        then:
        expirationRepository.count() == 1L
        def expirations = expirationRepository.findAll()
        expirations.any { it -> assertEqualsExpirations(it, expectedExpiration) }
    }

    def "RemoveExpiration removes books from existing expirations and if the expiration is empty deletes it"() {
        given:
        expirationRepository.save(new Expiration(expiresOn: date2, todayExpirations: [book1], futureExpirations: []))

        when:
        service.removeExpiration([book1])

        then:
        expirationRepository.count() == 0L
    }
}
