package com.kr.librarysystem.library

import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository
import com.kr.librarysystem.utils.DBSaver
import com.kr.librarysystem.utils.DateService
import com.kr.librarysystem.utils.TestDataG
import integrationTest.AbstractIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.text.SimpleDateFormat

class OverdueCalculatorSpec extends AbstractIntegrationTest {

    @Autowired
    BookRepository bookRepository

    @Autowired
    DBSaver dbSaver

    @Autowired
    LibraryMemberRepository libraryMemberRepository

    @Transactional
    def "AssignDebtToMembers"() {
    given:
    Date mockToday = new SimpleDateFormat("YYYY-MM-dd").parse("2018-01-01")
    def beforeMockToday= new SimpleDateFormat("YYYY-MM-dd").parse("2017-12-01")
    def afterMockToday= new SimpleDateFormat("YYYY-MM-dd").parse("2018-02-01")

    def mockDateService = Stub(DateService)
    mockDateService.getTodayDate() >> mockToday

    def overdueMember = TestDataG.getLibraryMember1()
    def notOverdueMember = TestDataG.getLibraryMember2()
    overdueMember.setDebt(5)
    notOverdueMember.setDebt(5)

    def overdueBook = TestDataG.getBook1()
    def notOverdueBook = TestDataG.getBook2()
        overdueBook.setBorrowed(true)
        notOverdueBook.setBorrowed(true)
        overdueBook.setBorrowedUntil(beforeMockToday)
        notOverdueBook.setBorrowedUntil(afterMockToday)
        overdueBook.setBorrowedBy(overdueMember)
        notOverdueBook.setBorrowedBy(notOverdueMember)
    overdueBook.setDebtPerDay(5)
    notOverdueBook.setDebtPerDay(5)

    dbSaver.saveBooks([notOverdueBook, overdueBook])

        def overdueCalculator = new OverdueCalculator(mockDateService, bookRepository)

        when:
overdueCalculator.assignDebtToMembers()
        overdueMember = libraryMemberRepository.findOne(overdueMember.getId())
        notOverdueMember = libraryMemberRepository.findOne(notOverdueMember.getId())

        then:
        overdueMember.getDebt() == 10
        notOverdueMember.getDebt() == 5


    }
}
