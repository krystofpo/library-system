package com.kr.librarysystem.entities;

import org.springframework.util.CollectionUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

@Entity
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private Date membershipUntil;
    private final List<Book> borrowedBooks=new ArrayList<>();

    public LibraryMember() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getMembershipUntil() {
        return membershipUntil;
    }

    public void setMembershipUntil(Date membershipUntil) {
        this.membershipUntil = membershipUntil;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<Book> borrowBooks(List<Book> books) {
        borrowedBooks.addAll(books);
        removeDuplicates();
        return borrowedBooks;
    }

    private void removeDuplicates() {
        Set<Book> uniqueBooks = new HashSet<>();
        uniqueBooks.addAll(borrowedBooks);
        borrowedBooks.clear();
        borrowedBooks.addAll(uniqueBooks);
    }

    public List<Book> returnBooks(List<Book> books) {
        if (!CollectionUtils.isEmpty(books)) {
            borrowedBooks.removeAll(books);
        }
        return borrowedBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryMember)) return false;
        LibraryMember that = (LibraryMember) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
