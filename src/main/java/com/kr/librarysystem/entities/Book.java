package com.kr.librarysystem.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

@ManyToOne
    private Author author;
    private String title;
    private boolean borrowed=false;
    private Date borrowedUntil;

    @ManyToOne
    private LibraryMember borrowedBy;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Date getBorrowedUntil() {
        return borrowedUntil;
    }

    public void setBorrowedUntil(Date borrowedUntil) {
        this.borrowedUntil = borrowedUntil;
    }

    public LibraryMember getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(LibraryMember borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public void borrowMe(LibraryMember member) {
        setBorrowedBy(member);
        setBorrowed(true);
        setBorrowedUntil(calculateBorrowUntil());
    }

    public void returnMe() {
        setBorrowedBy(null);
        setBorrowedUntil(null);
        setBorrowed(false);
    }

    private Date calculateBorrowUntil() {
        return new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getTitle());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book: ");
        sb.append(author.toString());
        sb.append(": ");
        sb.append(title);
        return sb.toString();
    }
}
