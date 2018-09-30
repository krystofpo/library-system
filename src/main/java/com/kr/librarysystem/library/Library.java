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
    private ExpirationService expirationService;

    @Autowired
    public Library(LibraryMemberRepository libraryMemberRepository, BookRepository bookRepository, ExpirationService expirationService) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
        this.expirationService = expirationService;
    }

    @Transactional
    public void memberBorrowsBooks(LibraryMember libraryMember, List<Book> books) {
//todo verify that member has unexpired membership, throw exception, view will handle it
        assignMemberToBooks(libraryMember, books);
        assignBooksToMember(libraryMember, books);
        expirationService.addExpiration(books);
    }

    private void assignMemberToBooks(LibraryMember libraryMember, List<Book> books) {
        for (Book book : books) {
            book.borrowMe(libraryMember);
        bookRepository.save(book);
        }
    }

    private void assignBooksToMember(LibraryMember libraryMember, List<Book> books) {
        libraryMember.borrowBooks(books);
        libraryMemberRepository.save(libraryMember);
    }


    public void memberReturnsBooks(LibraryMember libraryMember, List<Book> books) {
        unassignMemberFromBooks(books);
        unassignBooksFromMember(libraryMember, books);
        expirationService.removeExpiration(books);
    }

    private void unassignBooksFromMember(LibraryMember libraryMember, List<Book> books) {
        libraryMember.returnBooks(books);
        libraryMemberRepository.save(libraryMember);
    }

    private void unassignMemberFromBooks(List<Book> books) {
        for (Book book : books) {
            book.returnMe();
            bookRepository.save(book);
        }
    }
}
