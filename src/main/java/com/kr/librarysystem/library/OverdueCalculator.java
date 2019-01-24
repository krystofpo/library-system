package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.LibraryMember;
import com.kr.librarysystem.persistence.BookRepository;
import com.kr.librarysystem.utils.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OverdueCalculator {

    private DateService dateService;
    private BookRepository bookRepository;

    @Autowired
    public OverdueCalculator(DateService dateService, BookRepository bookRepository) {
        this.dateService = dateService;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void assignDebtToMembers(){

        List<Book> overdueBooks = getOverdueBooks();
        assignDebtToMembers(overdueBooks);
    }

    private void assignDebtToMembers(List<Book> overdueBooks) {
        for (Book book : overdueBooks) {
            int debt = book.getDebtPerDay();
            LibraryMember member = book.getBorrowedBy();
            member.setDebt(member.getDebt() + debt);
        }
    }

    private List<Book> getOverdueBooks() {

        Date today = dateService.getTodayDate();
        return
                bookRepository.findByBorrowedTrueAndBorrowedUntilBefore(today);
    }
}
