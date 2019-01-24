package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findByBorrowedTrueAndBorrowedUntilBefore(Date date);


}
