package com.github.msafriends.moduleapi.dto.response.member;

import com.github.msafriends.modulecore.domain.member.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupResponse {

    private String email;
    private String name;

    @Builder
    private MemberSignupResponse(final String email, final String name) {
        this.email = email;
        this.name = name;
    }

    public static MemberSignupResponse from(Member member) {
        return MemberSignupResponse.builder()
                .email(member.getEmail().getValue())
                .name(member.getName())
                .build();
    }
}
