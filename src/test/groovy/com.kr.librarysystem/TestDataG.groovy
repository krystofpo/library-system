package com.kr.librarysystem

import com.kr.librarysystem.entities.Author
import com.kr.librarysystem.entities.Book
import com.kr.librarysystem.entities.LibraryMember

class TestDataG {

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
}
