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
    LibraryMemberRepository memberRepository = Mock()
    BookRepository bookRepository = Mock()
    ExpirationService expirationService = Mock()
    Library library = new Library(memberRepository, bookRepository, expirationService)
    def member = new LibraryMember()

    void setup() {
    }

    def 'borrow books saves books and library member and creates expiration record'() {
        when:
        library.memberBorrowsBooks(member, [book1, book2, book3])

        then:
        member.borrowedBooks.size() == 3
        member.borrowedBooks.containsAll([book1, book2, book3])
        member.borrowedBooks.stream()
                .allMatch{ book -> book.borrowed == true && book.borrowedBy == member && book.borrowedUntil != null }
        1 * memberRepository.save(member)
        3 * bookRepository.save(_)
        1 * expirationService.addExpiration([book1, book2, book3])
    }

    def 'return books saves books and library member and removes expiration'() {
        given:
                member.getBorrowedBooks().addAll([book1, book2, book3])
        [book1, book2, book3].forEach{book ->
            book.setBorrowedBy(member)
            book.setBorrowedUntil(new Date())
            book.setBorrowed(true)
        }

        when:
        library.memberReturnsBooks(member, [book1, book3])

        then:
        member.borrowedBooks.size() == 1
        member.borrowedBooks.containsAll([book2])
        member.borrowedBooks.stream()
                .allMatch() { book -> book.borrowed == true && book.borrowedBy == member }
        [book1, book3].stream()
                .allMatch{book -> book.borrowed==false && book.borrowedBy == null && book.borrowedUntil == null}
        1 * memberRepository.save(member)
        2 * bookRepository.save(_)
        1 * expirationService.removeExpiration([book1, book3])
    }


}
