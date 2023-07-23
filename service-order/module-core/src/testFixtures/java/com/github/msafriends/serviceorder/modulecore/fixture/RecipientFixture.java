package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.RecipientRequest;

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

    public static RecipientRequest createDefaultRecipientRequest() {
        return RecipientRequest.builder()
                .name(DEFAULT_NAME)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .address(DEFAULT_ADDRESS)
                .build();
    }
}
