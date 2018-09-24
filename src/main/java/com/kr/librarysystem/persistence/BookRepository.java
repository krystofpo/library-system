package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
