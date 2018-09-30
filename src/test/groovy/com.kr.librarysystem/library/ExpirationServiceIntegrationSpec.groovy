package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.entities.Expiration
import com.kr.librarysystem.persistence.ExpirationRepository
import com.kr.librarysystem.utils.DateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ExpirationServiceIntegrationSpec extends Specification {

@Autowired
    ExpirationRepository expirationRepository = Mock()

    @Autowired
    EntityManager em

    DateService dateService = Mock()

    def service = new ExpirationService(expirationRepository, dateService)
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
    def borrowedBook = TestDataG.getBook5()
    private String date1
    private String date2

    def setup() {
        dateService.todayLocalDate >> LocalDate.parse('2018-08-01', DateTimeFormatter.ISO_LOCAL_DATE)
        book1.setBorrowingPeriod(Period.ofDays(5))      //date2 should go to expiraiton2: today expiration
        book1.setNotificationPeriod(Period.ofDays(2))   //date1 should go to expiraiton1: future expiration
        book2.setBorrowingPeriod(Period.ofDays(2))      //date1 should go to expiraiton1: today expiraiton
        book2.setNotificationPeriod(Period.ofDays(1))   //2018-08-02 non existing expiration, future expiration
        TestDataG.saveBooks([book1, book2], em)
        date1 = '2018-08-03'
        date2 = '2018-08-06'
        def actualExpiration1
        def expectedExpiration1
        //todo
    }

    def "AddExpiration looks up existing expirations and adds borrowed books or creates new expirations"() {
        given:
        def matchExpiration1 = { Expiration e = it
        e.getExpiresOn()== date1 &&
        e.getFutureExpirations().size()==2 &&
                e.getFutureExpirations().containsAll([borrowedBook, book1])
        e.getTodayExpirations()==[book2]}

def matchExpiration2 = { Expiration e = it
        e.getExpiresOn()=='2018-08-02' &&
        e.getFutureExpirations()==[book2] &&
        e.getTodayExpirations()==[]}


        def matchExpiration3 = { Expiration e = it
        e.getExpiresOn()== date2 &&
        e.getFutureExpirations()==[borrowedBook] &&
        e.getTodayExpirations()==[book1]}

        expirationRepository.save(new Expiration(futureExpirations: [borrowedBook], expiresOn: date1))
expirationRepository.save(new Expiration(futureExpirations: [borrowedBook], expiresOn: date2))


        when:
        service.addExpiration([book1, book2])

        then:
1 * expirationRepository.save({matchExpiration1(it)})// order of invocation does not matter, Spock will match the calls
1 * expirationRepository.save({matchExpiration2(it)})
1 * expirationRepository.save({matchExpiration3(it)})

    }

    def "RemoveExpiration"() {
    }
}
