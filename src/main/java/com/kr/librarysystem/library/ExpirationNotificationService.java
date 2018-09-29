package com.kr.librarysystem.library;

import com.kr.librarysystem.email.EmailFormatter;
import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.Expiration;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.ExpirationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
public class ExpirationNotificationService {

    private ExpirationRepository expirationRepository;
    private EmailFormatter emailFormatter;

    @Autowired
    public ExpirationNotificationService(ExpirationRepository expirationRepository, EmailFormatter emailFormatter) {
        this.expirationRepository = expirationRepository;
        this.emailFormatter = emailFormatter;
    }


    public void notifyMembers() {
        Expiration expiration = getExpirationForToday();
        notifyMembersAboutTodayExpirations(expiration.getTodayExpirations());
        notifyMembersAboutFutureExpirations(expiration.getFutureExpirations());
        expirationRepository.delete(expiration);
    }

    private Expiration getExpirationForToday() {
        String today = LocalDate.now().toString();
        return expirationRepository.findByExpiresOn(today).get(0);
    }

    void notifyMembersAboutFutureExpirations(List<Book> expiringBooks) {
        Map<LibraryMember, List<Book>> expiringBooksPerMember = expiringBooks.stream().collect(
                Collectors.groupingBy(Book::getBorrowedBy));
        expiringBooksPerMember.values()
                .forEach(books -> emailFormatter.formatAndSendMailForFuture(books));
    }

    void notifyMembersAboutTodayExpirations(List<Book> expiringBooks) {
        Map<LibraryMember, List<Book>> expiringBooksPerMember = expiringBooks.stream().collect(
                Collectors.groupingBy(Book::getBorrowedBy));
        expiringBooksPerMember.values()
                .forEach(books -> emailFormatter.formatAndSendMailForToday(books));
    }
}
