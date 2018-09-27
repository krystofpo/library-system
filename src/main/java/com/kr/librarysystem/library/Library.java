package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.BookRepository;
import com.kr.librarysystem.persistence.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Library {

    private LibraryMemberRepository libraryMemberRepository;
    private BookRepository bookRepository;

    @Autowired
    public Library(LibraryMemberRepository libraryMemberRepository, BookRepository bookRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public LibraryMember borrowBooks(LibraryMember libraryMember, List<Book> books) {
//todo verify that member has unexpired membership, throw exception, view will handle it

        updateBooks(libraryMember, books);
        updateMember(libraryMember, books);
        return null;
    }

    private void updateBooks(LibraryMember libraryMember, List<Book> books) {
        for (Book book : books) {
            book.borrowMe(libraryMember);
        bookRepository.save(book);
        }
    }

    private void updateMember(LibraryMember libraryMember, List<Book> books) {
        libraryMember.borrowBooks(books);
        libraryMemberRepository.save(libraryMember);
    }
}
