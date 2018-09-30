package com.kr.librarysystem.utils;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class DateService {

    public Date getTodayDate() {
        return new Date();
    }

    public LocalDate getTodayLocalDate() {
        return LocalDate.now();
    }
}
