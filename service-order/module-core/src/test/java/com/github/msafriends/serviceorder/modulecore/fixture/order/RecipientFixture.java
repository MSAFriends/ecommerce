package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;

public class RecipientFixture {
    private static final String DEFAULT_NAME = "테스트 수신인";
    private static final String DEFAULT_PHONE_NUMBER = "010-1234-5678";
    private static final String DEFAULT_ADDRESS = "테스트 주소";

    public static Recipient createDefaultRecipient() {
        return Recipient.builder()
                .name(DEFAULT_NAME)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .address(DEFAULT_ADDRESS)
                .build();
    }

    public static Recipient createCustomRecipient(String name, String phoneNumber, String address) {
        return Recipient.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
