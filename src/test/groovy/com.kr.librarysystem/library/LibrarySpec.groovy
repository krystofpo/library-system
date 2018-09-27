package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.entities.LibraryMember
import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository
import spock.lang.Specification


class LibrarySpec extends Specification {

    private def book1 = TestDataG.getBook1()
    private def book2 = TestDataG.getBook2()
    private def book3 = TestDataG.getBook3()

    void setup() {
    }

    def 'borrow books saves books and library member'() {
        given:
        LibraryMemberRepository memberRepository = Mock()
        BookRepository bookRepository = Mock()
        Library library = new Library(memberRepository, bookRepository)
        def member = new LibraryMember()

        when:
        library.borrowBooks(member, [book1, book2, book3])

        then:
        member.borrowedBooks.size() == 3
        member.borrowedBooks.containsAll([book1, book2, book3])
        member.borrowedBooks.stream()
                .filter { book -> book.borrowed == true && book.borrowedBy == member }
                .count() == 3L
        1 * memberRepository.save(member)
        3 * bookRepository.save(_)

    }


}
