package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.Author;
import com.kr.librarysystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
