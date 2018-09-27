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

    def 'borrowing saves to repositories'() {
        given:
        authorRepository.save(book1.getAuthor())
        authorRepository.save(book2.getAuthor())
        bookRepository.save(book1)
        bookRepository.save(book2)
        libraryMemberRepository.save(libraryMember)


        when:
        library.borrowBooks(libraryMember, [book1, book2])

        then:
        libraryMemberRepository.count() == 1L
        libraryMemberRepository.findAll().get(0).getBorrowedBooks()
                .stream().anyMatch { book -> book.getTitle().contains('Malostranske') }

        bookRepository.count() == 2L
        bookRepository.findAll().get(0).getBorrowedBy().getFirstName() == 'Josef'
    }
}
