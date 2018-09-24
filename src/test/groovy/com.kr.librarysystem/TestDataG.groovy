package com.kr.librarysystem

import com.kr.librarysystem.entities.Author
import com.kr.librarysystem.entities.Book

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
}
