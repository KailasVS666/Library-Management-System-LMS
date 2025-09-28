package com.lms.repository;

import com.lms.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Member findByEmail(String email);

    // New method for paginated search by name
    Page<Member> findByFirstNameContaining(String keyword, Pageable pageable);
}