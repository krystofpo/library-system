package com.kr.librarysystem.library

import com.kr.librarysystem.utils.TestDataG
import com.kr.librarysystem.email.EmailFormatter
import com.kr.librarysystem.email.EmailSender
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.AuthorRepository
import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.ExpirationRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository
import com.kr.librarysystem.utils.DBSaver
import com.kr.librarysystem.utils.DateService
import integrationTest.AbstractIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


@Transactional
class SchedulerAndExpirationServiceIntegrationSpec extends AbstractIntegrationTest {

    @Autowired
    ExpirationRepository expirationRepository

    @Autowired
    BookRepository bookRepository

    @Autowired
    AuthorRepository authorRepository

    @Autowired
    LibraryMemberRepository libraryMemberRepository

    @Autowired
    DBSaver dbSaver

    @Autowired
    EmailSender emailSender

    DateService dateService = Mock()
    EmailFormatter emailFormatter

    ExpirationService service
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
   def book3 = TestDataG.getBook3()
    private String date1
    private String date2
    private String date3
    Closure<Boolean> assertEqualsExpirations

    def setup() {
        emailFormatter = new EmailFormatter(emailSender)
        service = new ExpirationService(expirationRepository, dateService, emailFormatter)//todo move to declaration
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

        TestDataG.persistBooksAndNestedObjects([book1, book2, book3], bookRepository, authorRepository, libraryMemberRepository)


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

    def "AddExpiration creates new expirations"() { //todo refactor to unit test, mock repository
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

    def 'addExpiration finds existing Expiration and adds to future expirations'() {//todo refactor to unit test, mock repository
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

    def 'addExpiration finds existing Expiration and adds to today expirations'() { //todo refactor to unit test, mock repository
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

    def "RemoveExpiration removes books from existing expirations - today books"() { //todo refactor to unit test, mock repository
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

    def "RemoveExpiration removes books from existing expirations - future books"() { //todo refactor to unit test, mock repository
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

    def "RemoveExpiration removes books from existing expirations and if the expiration is empty deletes it"() { //todo refactor to unit test, mock repository
        given:
        expirationRepository.save(new Expiration(expiresOn: date2, todayExpirations: [book1], futureExpirations: []))

        when:
        service.removeExpiration([book1])

        then:
        expirationRepository.count() == 0L
    }

@Transactional //todo ma by tranacitonal? na konci se to rollbakcne, ale kdyz expiraiton vymaze mezitim  expiration tak se nebude moct vymazat v testu
    def "Scheduler should send email notifications for expirations"() { //todo add scheduler fucnitnality, jak to mam autorieovat? co bdue vytvorney dvakrt?
        //todo ejdno v konextu ejednou v tesu? serivce bude dvakrta
        given:
        def book1 = TestDataG.getBook1()//todo refactor where it shoud be shared and intitilaized
        def book2 = TestDataG.getBook2()
        def book3 = TestDataG.getBook3()
        def book4 = TestDataG.getBook4()
        def member1 = TestDataG.getLibraryMember1()
        def member2 = TestDataG.getLibraryMember2()
        member1.setEmail('email1')
        member2.setEmail('email2')
        book1.setBorrowedBy(member1)
        book2.setBorrowedBy(member2)
        book3.setBorrowedBy(member2)
        book4.setBorrowedBy(member2)
        book1.setBorrowedUntil(new Date())
        book2.setBorrowedUntil(new Date())
        book3.setBorrowedUntil(new Date())
        book4.setBorrowedUntil(new Date())
        String today = new SimpleDateFormat('yyyy-MM-dd').format(new Date())
        def expires = new Expiration(expiresOn: today, todayExpirations: [book1, book2, book3], futureExpirations: [book4])


        //saving has to be in a different class with transactional requires new, so it gets commited
        dbSaver.saveExpiration(expires)
        long count = expirationRepository.count()
    println(count)


        when:
       //LibraryScheduler is fired meanwhile, should find expiration and call EmailSender
        Thread.sleep(8*1000L)

        then:
        1 * emailSender.sendEmail('email1', { it.contains('dnes') }, { it.contains('Neruda') })
        1 * emailSender.sendEmail('email2', { it.contains('dnes') }, { it.contains('Valka') && it.contains('Saman') })
        1 * emailSender.sendEmail('email2', { it.contains('brzy') }, { it.contains('1984') })
        expirationRepository.count()==0L
    }
}
