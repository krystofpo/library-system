package com.kr.librarysystem.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Expiration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String expiresOn;

    @OneToMany
    private List<Book> todayExpirations = new ArrayList<>();

    @OneToMany
    private List<Book> futureExpirations = new ArrayList<>();


    public Expiration() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getTodayExpirations() {
        return todayExpirations;
    }

    public void setTodayExpirations(List<Book> todayExpirations) {
        this.todayExpirations = todayExpirations;
    }

    public List<Book> getFutureExpirations() {

        return futureExpirations;
    }

    public void addToTodayExpirations(Book book) {
        todayExpirations.add(book);
    }

    public void addToFutureExpirations(Book book) {
        futureExpirations.add(book);
    }

    public void removeFromTodayExpirations(Book book) {
        todayExpirations.remove(book);
    }

    public void removeFromFutureExpirations(Book book) {
        futureExpirations.remove(book);
    }

    public void setFutureExpirations(List<Book> futureExpirations) {
        this.futureExpirations = futureExpirations;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expiration)) return false;
        Expiration that = (Expiration) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
