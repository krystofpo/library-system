package com.kr.librarysystem.persistence;

import com.kr.librarysystem.entities.LibraryMember;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<LibraryMember, Long> {
}
