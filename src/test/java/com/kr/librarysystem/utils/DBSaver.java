package com.kr.librarysystem.utils;

import com.kr.librarysystem.entities.Author;
import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.Expiration;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.AuthorRepository;
import com.kr.librarysystem.persistence.BookRepository;
import com.kr.librarysystem.persistence.ExpirationRepository;
import com.kr.librarysystem.persistence.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DBSaver {

    @Autowired
    private ExpirationRepository expirationRepository;

    @Autowired
    private AuthorRepository authorRepository;

            @Autowired
            private BookRepository bookRepository;

            @Autowired
            private LibraryMemberRepository libraryMemberRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveExpiration(Expiration expiration) {
        saveAuthors(expiration);
        saveMembers(expiration);
        saveBooks(expiration);
        expirationRepository.save(expiration);
    }


    private void saveAuthors(Expiration expiration) {
        saveAuthors(expiration.getFutureExpirations());
        saveAuthors(expiration.getTodayExpirations());
    }


    private void saveMembers(Expiration expiration) {
        saveMembers(expiration.getFutureExpirations());
        saveMembers(expiration.getTodayExpirations());
    }

    private void saveBooks(Expiration expiration) {
        saveBooksp(expiration.getFutureExpirations());
        saveBooksp(expiration.getTodayExpirations());
    }


    private  void saveAuthors(List<Book> books) {
        for (Book book : books) {
            Author author = book.getAuthor();
            if (author != null) {
                authorRepository.save(author);
            }
        }
    }

    private void saveMembers(List<Book> books) {
        for (Book book : books) {
            LibraryMember member = book.getBorrowedBy();
            if (member != null) {
                libraryMemberRepository.save(member);
            }
        }
    }

    private void saveBooksp(List<Book> books) {
        for (Book book : books) {
            bookRepository.save(book);
        }
    }

    @Transactional
    public void saveBooks(List<Book> books) {
        saveAuthors(books);
        saveMembers(books);
        saveBooksp(books);
    }
}
