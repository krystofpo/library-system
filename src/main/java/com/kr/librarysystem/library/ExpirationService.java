package com.kr.librarysystem.library;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.persistence.ExpirationRepository;
import com.kr.librarysystem.utils.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpirationService {

    private ExpirationRepository expirationRepository;
    private DateService dateService;

    @Autowired
    public ExpirationService(ExpirationRepository expirationRepository, DateService dateService) {
        this.expirationRepository = expirationRepository;
        this.dateService = dateService;
    }

    public void addExpiration(List<Book> books) {

    }

    public void removeExpiration(List<Book> books) {

    }


}
