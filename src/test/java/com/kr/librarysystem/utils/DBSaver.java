package com.kr.librarysystem.utils;

import com.kr.librarysystem.entities.Author;
import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.Expiration;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.AuthorRepository;
import com.kr.librarysystem.persistence.BookRepository;
import com.kr.librarysystem.persistence.ExpirationRepository;
import com.kr.librarysystem.persistence.LibraryMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DBSaver {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveExpiration(Expiration expiration, ExpirationRepository expirationRepository, AuthorRepository authorRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        saveAuthors(expiration, authorRepository);
        saveMembers(expiration, libraryMemberRepository);
        saveBooks(expiration, bookRepository);
        expirationRepository.save(expiration);
    }

    private void saveAuthors(Expiration expiration, AuthorRepository authorRepository) {
        saveAuthors(expiration.getFutureExpirations(), authorRepository);
        saveAuthors(expiration.getTodayExpirations(), authorRepository);
    }


    private void saveMembers(Expiration expiration, LibraryMemberRepository libraryMemberRepository) {
        saveMembers(expiration.getFutureExpirations(), libraryMemberRepository);
        saveMembers(expiration.getTodayExpirations(), libraryMemberRepository);
    }

    private void saveBooks(Expiration expiration, BookRepository bookRepository) {
        saveBooks(expiration.getFutureExpirations(), bookRepository);
        saveBooks(expiration.getTodayExpirations(), bookRepository);
    }


    private  void saveAuthors(List<Book> books, AuthorRepository authorRepository) {
        for (Book book : books) {
            Author author = book.getAuthor();
            if (author != null) {
                authorRepository.save(author);
            }
        }
    }

    private void saveMembers(List<Book> books, LibraryMemberRepository repo) {
        for (Book book : books) {
            LibraryMember member = book.getBorrowedBy();
            if (member != null) {
                repo.save(member);
            }
        }
    }

    private void saveBooks(List<Book> books, BookRepository repo) {
        for (Book book : books) {
            if (book != null) {
                repo.save(book);
            }
        }
    }
}
