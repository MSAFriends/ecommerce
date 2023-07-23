package com.github.msafriends.modulecore.repository.member;

import com.github.msafriends.modulecore.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
