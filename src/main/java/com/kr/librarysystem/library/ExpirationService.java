package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.Expiration;
import com.kr.librarysystem.persistence.ExpirationRepository;
import com.kr.librarysystem.utils.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpirationService {

    private ExpirationRepository expirationRepository;
    private DateService dateService;
    private DateTimeFormatter formatter;

    @Autowired
    public ExpirationService(ExpirationRepository expirationRepository, DateService dateService) {
        this.expirationRepository = expirationRepository;
        this.dateService = dateService;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Transactional
    public void addExpiration(List<Book> books) {
        for (Book book : books) {
            addRecordForTodayExpiration(book);
            addRecordForFutureExpiration(book);
        }
    }

    private void addRecordForFutureExpiration(Book book) {
        String futureDate = calculateFutureDate(book);
        List<Expiration> expirationForFutureDate = expirationRepository.findByExpiresOn(futureDate);
        if (expirationForFutureDate.isEmpty()) {
            createFutureInNewExpiration(book, futureDate);
        } else {
            addFutureToExistingExpiration(book, expirationForFutureDate);
        }
    }

    private String calculateFutureDate(Book book) {
        return calculateDate(book.getNotificationPeriod());
    }

    private String calculateDate(Period period) {
        LocalDate date = dateService.getTodayLocalDate().plus(period);
        return date.format(formatter);
    }

    private void addFutureToExistingExpiration(Book book, List<Expiration> expirations) {
        Expiration expiration = expirations.get(0);
        expiration.addToFutureExpirations(book);
        expirationRepository.save(expiration);
    }

    private void createFutureInNewExpiration(Book book, String futureDate) {
        Expiration expiration = new Expiration();
        expiration.setExpiresOn(futureDate);
        expiration.addToFutureExpirations(book);
        expirationRepository.save(expiration);
    }

    private void addRecordForTodayExpiration(Book book) {
        String todayDate = calculateTodayDate(book);
        List<Expiration> expirationForTodayDate = expirationRepository.findByExpiresOn(todayDate);
        if (expirationForTodayDate.isEmpty()) {
            createTodayInNewExpiration(book, todayDate);
        } else {
            addTodayToExistingExpiration(book, expirationForTodayDate); //todo add get 0
        }

    }

    private void addTodayToExistingExpiration(Book book, List<Expiration> expirations) {
        Expiration expiration = expirations.get(0);
        expiration.addToTodayExpirations(book);
        expirationRepository.save(expiration);
    }

    private void createTodayInNewExpiration(Book book, String todayDate) {
        Expiration expiration = new Expiration();
        expiration.setExpiresOn(todayDate);
        expiration.addToTodayExpirations(book);
        expirationRepository.save(expiration);
    }

    private String calculateTodayDate(Book book) {
        return calculateDate(book.getBorrowingPeriod());
    }

    @Transactional
    public void removeExpiration(List<Book> books) {
        for (Book book : books) {
            removeRecordFromExpirations(book);
        }
    }

    private void removeRecordFromExpirations(Book book) {
        List<Expiration> expirations = findExpirationsThatContain(book);
        expirations.forEach(expiration -> {removeBookFromExpiration(expiration, book);
        deleteExpirationIfEmpty(expiration);});
    }

    private void deleteExpirationIfEmpty(Expiration expiration) {
        if (expiration.getFutureExpirations().isEmpty() && expiration.getTodayExpirations().isEmpty()) {
            expirationRepository.delete(expiration);
        }
    }

    private void removeBookFromExpiration(Expiration expiration, Book book) {
        expiration.removeFromTodayExpirations(book);
        expiration.removeFromFutureExpirations(book);
    }

    private List<Expiration> findExpirationsThatContain(Book book) {
        List<Expiration> expirations = expirationRepository.findByTodayExpirationsContains(book);
        expirations.addAll(expirationRepository.findByFutureExpirationsContains(book));
        return expirations;
    }


}
