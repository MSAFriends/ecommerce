package com.github.msafriends.modulecore.repository.member;

import com.github.msafriends.modulecommon.exception.member.member.MemberNotExistException;
import com.github.msafriends.modulecore.domain.member.Email;
import com.github.msafriends.modulecore.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(Email email);

    default Member findByEmailOrElseThrow(Long memberId) {
        return this.findById(memberId).orElseThrow(() ->
                new MemberNotExistException(memberId)
        );
    }
}
