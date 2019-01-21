package com.kr.librarysystem.utils

import com.kr.librarysystem.entities.Author
import com.kr.librarysystem.entities.Book
import com.kr.librarysystem.entities.LibraryMember
import com.kr.librarysystem.persistence.AuthorRepository
import com.kr.librarysystem.persistence.BookRepository
import com.kr.librarysystem.persistence.LibraryMemberRepository

class TestDataG { //toto merge db saver and helper tgoer in one groovy class

    static Book getBook1() {
        new Book(
                author: new Author(firstName: 'Jan', lastName: 'Neruda'),
                title: 'Povidky Malostranske')
    }

    static Book getBook2() {
        new Book(
                author: new Author(firstName: 'Karel', lastName: 'Capek'),
                title: 'Valka s Loky')
    }

    static Book getBook3() {
        new Book(
                author: new Author(firstName: 'Egon', lastName: 'Bondy'),
                title: 'Saman')
    }

    static LibraryMember getLibraryMember1() {
        new LibraryMember(firstName: 'Josef', lastName: 'Novak')
    }

    static LibraryMember getLibraryMember2() {
        new LibraryMember(firstName: 'Felix', lastName: 'Holzman')
    }

    static LibraryMember getLibraryMember3() {
        new LibraryMember(firstName: 'Emil', lastName: 'Kralicek')
    }

    static Book getBook4() {
        new Book(
                author: new Author(firstName: 'George', lastName: 'Orwell'),
                title: '1984')
    }

    static Book getBook5() {
        new Book(
                author: new Author(firstName: 'Krystof', lastName: 'Polansky'),
                title: 'Learn Java 13 in 3 Days')
    }

    static void persistBooksAndNestedObjects(List<Book> books, BookRepository bookRepository, AuthorRepository authorRepository, LibraryMemberRepository libraryMemberRepository) {
        saveAuthors(books, authorRepository)
        saveMembers(books, libraryMemberRepository)
        saveBooks(books, bookRepository)
    }

    private static void saveAuthors(List<Book> books, AuthorRepository authorRepository) {
        books.each {
            def author = it.getAuthor()
            if (author != null) {
                authorRepository.save(author)
            }
        }
    }

    private static void saveMembers(List<Book> books, LibraryMemberRepository repo) {
        books.each {
            def member = it.getBorrowedBy()
            if (member != null) {
                repo.save(member)
            }
        }
    }

    private static void saveBooks(List<Book> books, BookRepository repo) {
        books.each {
            repo.save(it)
        }
    }
}
