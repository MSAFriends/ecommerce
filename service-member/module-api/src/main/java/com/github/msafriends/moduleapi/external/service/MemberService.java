package com.github.msafriends.moduleapi.external.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberSignupRequest;
import com.github.msafriends.moduleapi.dto.response.member.MemberSignupResponse;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberSignupResponse createMember(MemberSignupRequest memberSignupRequest) {
        Member member = memberSignupRequest.toMember();
        memberRepository.save(member);
        return MemberSignupResponse.from(member);
    }
}
