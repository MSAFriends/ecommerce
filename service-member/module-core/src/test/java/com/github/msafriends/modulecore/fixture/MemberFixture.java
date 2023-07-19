package com.github.msafriends.modulecore.fixture;

import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.domain.member.Seller;

public class MemberFixture {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "테스트 요청사항";
    private static final String PHONE_NUMBER = "010-1234-5678";
    private static final String NAME = "admin";
    private static final String SELLER_NAME = "김경식";
    private static final String SELLER_NICK_NAME = "oereo";
    private static final int GRD = 1;

    public static Member createMember() {
        return Member.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .name(NAME)
                .build();
    }

    public static Seller createSeller() {
        return Seller.builder()
                .nickName(SELLER_NICK_NAME)
                .userName(SELLER_NAME)
                .grd(GRD)
                .build();
    }
}
