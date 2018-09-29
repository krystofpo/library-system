package com.kr.librarysystem.email

import com.kr.librarysystem.TestDataG
import spock.lang.Specification

import java.text.SimpleDateFormat

class EmailFormatterSpec extends Specification {

    def emailSender = Mock(EmailSender)
    def emailFormatter = new EmailFormatter(emailSender)
    def member = TestDataG.getLibraryMember1()
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()

    def setup() {
        member.setEmail('josef@novak.cz')
        Date date = new SimpleDateFormat('yyyy MM dd').parse('2018 07 01')
        [book1, book2].each {
            it.setBorrowedBy(member)
            it.setBorrowedUntil(date)
        }
    }

    def "FormatAndSendMailForToday"() {
        given:
        def subjectMatcher = { it.contains('dnes zacina nabihat') } // do Closure vstupuje parametr String it
        def messageMatcher = {
            it.contains('Neruda') && it.contains('Povidky') && it.contains('Capek') && it.contains('Valka') && it.contains('Pujceno do: 01.07.2018')
        }

        when:
        emailFormatter.formatAndSendMailForToday([book1, book2])

        then:
        1 * emailSender.sendEmail('josef@novak.cz',{subjectMatcher(it)} , {messageMatcher(it)}) // Spock pro argument matchery bere jen Closure literaly a ne reference na Closure, ale takto lze obejit
    }

    def "FormatAndSendMailForFuture"() {
        given:
        def subjectMatcher = { it.contains('brzy vyprsi') } // do Closure vstupuje parametr String it
        def messageMatcher = {
            it.contains('Neruda') && it.contains('Povidky') && it.contains('Capek') && it.contains('Valka') && it.contains('Pujceno do: 01.07.2018')
        }

        when:
        emailFormatter.formatAndSendMailForFuture([book1, book2])

        then:
        1 * emailSender.sendEmail('josef@novak.cz',{subjectMatcher(it)} , {messageMatcher(it)}) // Spock pro argument matchery bere jen Closure literaly a ne reference na Closure, ale takto lze obejit
    }
}
