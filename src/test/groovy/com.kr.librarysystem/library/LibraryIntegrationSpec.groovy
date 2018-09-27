package com.kr.librarysystem.library

import com.kr.librarysystem.TestDataG
import com.kr.librarysystem.persistence.AuthorRepository
import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LibraryIntegrationSpec extends Specification {


    @Autowired
    private Library library

    @Autowired
    private LibraryMemberRepository libraryMemberRepository

    @Autowired
    private BookRepository bookRepository

    @Autowired
    private AuthorRepository authorRepository

    def setup() {
        bookRepository.deleteAll()
        libraryMemberRepository.deleteAll()
        authorRepository.deleteAll()
    }

    def cleanup() {
        bookRepository.deleteAll()
        libraryMemberRepository.deleteAll()
        authorRepository.deleteAll()
    }

    def libraryMember = TestDataG.getLibraryMember()
    def book1 = TestDataG.getBook1()
    def book2 = TestDataG.getBook2()
    def book3 = TestDataG.getBook3()

    def 'borrowing and returning saves to repositories'() {
        given:
        authorRepository.save(book1.getAuthor())
        authorRepository.save(book2.getAuthor())
        authorRepository.save(book3.getAuthor())

        bookRepository.save(book1)
        bookRepository.save(book2)
        bookRepository.save(book3)

        libraryMemberRepository.save(libraryMember)

        when:
        library.memberBorrowsBooks(libraryMember, [book1, book2])
        library.memberReturnsBooks(libraryMember, [book2])
        library.memberBorrowsBooks(libraryMember, [book3])

        then:
        libraryMemberRepository.count() == 1L
        def actualBorrowedBooks = libraryMemberRepository.findAll().get(0).getBorrowedBooks()
        actualBorrowedBooks.size() == 2
        actualBorrowedBooks.containsAll([book1, book3])
        actualBorrowedBooks.stream().allMatch { book -> book.getBorrowedBy() == libraryMember && book.getBorrowedUntil() != null && book.isBorrowed() == true }

        bookRepository.count() == 3L
        bookRepository.findAll().stream()
                .filter { book -> book.borrowed == false && book.borrowedUntil == null && book.borrowedBy == null }.count() == 1L
    }
}
