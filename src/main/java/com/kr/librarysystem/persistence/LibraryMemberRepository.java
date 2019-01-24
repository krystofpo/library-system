package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {

    public List<LibraryMember> findByFirstNameAndLastName(String firstName, String lastName);
}
