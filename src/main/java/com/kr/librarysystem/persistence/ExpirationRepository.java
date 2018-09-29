package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.Book;
import com.kr.librarysystem.entities.Expiration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpirationRepository extends JpaRepository<Expiration, Long> {

    List<Expiration> findByExpiresOn(String expires);

}
