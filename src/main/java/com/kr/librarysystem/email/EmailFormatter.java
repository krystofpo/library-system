package com.kr.librarysystem.email;

import com.kr.librarysystem.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EmailFormatter {

    private EmailSender emailSender;
    private String todaySubject="Upozorneni: dnes zacina nabihat zpozdne";
    private String futureSubject="Upozorneni: brzy vyprsi vypujcky";
    private SimpleDateFormat dateFormat;

    @Autowired
    public EmailFormatter(EmailSender emailSender) {
        this.emailSender = emailSender;
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    public void formatAndSendMailForToday(List<Book> books) {
        String to = getEmail(books);
        String message = getMessageForToday(books);
        emailSender.sendEmail(to, todaySubject, message);
    }

    private String getMessageForToday(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dobry den,\r\n dnes je treba vratit nasledujici jednotky, jinak zacne nabihat zpozdne:\r\n");
        sb.append(formatBorrowedUnits(books));
        return sb.toString();
    }

    private String formatBorrowedUnits(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString());
            sb.append(". Pujceno do: ");
            sb.append(dateFormat.format(book.getBorrowedUntil()));
            sb.append("\r\n");
        }
        return sb.toString();
    }

    private String getEmail(List<Book> books) {
        return books.get(0).getBorrowedBy().getEmail();
    }

    public void formatAndSendMailForFuture(List<Book> books) {
        String to = getEmail(books);
        String message = getMessageForFuture(books);
        emailSender.sendEmail(to, futureSubject, message);
    }

    private String getMessageForFuture(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dobry den,\r\n brzy bude treba vratit nasledujici jednotky:\r\n");
        sb.append(formatBorrowedUnits(books));
        return sb.toString();
    }
}
