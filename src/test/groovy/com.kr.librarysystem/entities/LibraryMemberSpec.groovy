package com.kr.librarysystem.entities


import com.kr.librarysystem.utils.TestDataG
import spock.lang.Specification

class LibraryMemberSpec extends Specification {

    private def book1 = TestDataG.getBook1()
    private def book2 = TestDataG.getBook2()
    private def book3 = TestDataG.getBook3()

    void setup() {
    }


    def "books are added to borrowed books"() {
        given:
        def libraryMember = new LibraryMember()

        when:
        libraryMember.borrowBooks([book2, book3])
        libraryMember.borrowBooks([book1])

        then:
        libraryMember.borrowedBooks.size()==3
        libraryMember.borrowedBooks.containsAll([book1, book2, book3])
    }

    def "same book cannot be borrowed twice"() {
        given:
        def libraryMember = new LibraryMember()

        when:
        libraryMember.borrowBooks([book2, book3])
        libraryMember.borrowBooks([book2])

        then:
        libraryMember.borrowedBooks.size()==2
        libraryMember.borrowedBooks.containsAll([book2, book3])
    }

    def "ReturnBooks"() {
        given:
        def libraryMember = new LibraryMember()
        libraryMember.borrowBooks([book1, book2, book3])

        when:
        libraryMember.returnBooks([book1, book3])

        then:
        libraryMember.borrowedBooks.size()==1
        libraryMember.borrowedBooks.containsAll([book2])
    }


}
