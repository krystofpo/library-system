package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.BookRepository;
import com.kr.librarysystem.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class Library {

    private MemberRepository memberRepository;
    private BookRepository bookRepository;

    @Autowired
    public Library(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public LibraryMember borrowBooks(LibraryMember libraryMember, List<Book> books) {
//todo verify that member has unexpired membership, throw exception, view will handle it
        updateMember(libraryMember, books);
        updateBooks(libraryMember, books);
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
        memberRepository.save(libraryMember);
    }
}
