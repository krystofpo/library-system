package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
}
