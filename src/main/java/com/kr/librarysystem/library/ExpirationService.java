package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.persistence.ExpirationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpirationService {

    private ExpirationRepository expirationRepository;

    @Autowired
    public ExpirationService(ExpirationRepository expirationRepository) {
        this.expirationRepository = expirationRepository;
    }

    public void addExpiration(List<Book> books) {

    }

    public void removeExpiration(List<Book> books) {

    }




}
