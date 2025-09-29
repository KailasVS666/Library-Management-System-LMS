package com.lms.service;

import com.lms.entity.Member;
import com.lms.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // New method for pagination
    public Page<Member> findPaginated(int pageNo, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword != null && !keyword.trim().isEmpty()) {
            return memberRepository.findByFirstNameContaining(keyword, pageable);
        }
        return memberRepository.findAll(pageable);
    }
    
    // Kept for other parts of the app that might need a full list
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Member saveMember(Member member) {
        Member existingMember = memberRepository.findByEmail(member.getEmail());
        if (existingMember != null && (member.getId() == null || !existingMember.getId().equals(member.getId()))) {
            throw new IllegalStateException("Email already registered: " + member.getEmail());
        }
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}