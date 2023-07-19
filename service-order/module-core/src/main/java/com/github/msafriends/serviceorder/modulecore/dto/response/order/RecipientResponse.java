package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipientResponse {
    private final String name;
    private final String phoneNumber;
    private final String address;

    @Builder
    public RecipientResponse(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static RecipientResponse from(Recipient recipient) {
        return RecipientResponse.builder()
                .name(recipient.getName())
                .phoneNumber(recipient.getPhoneNumber())
                .address(recipient.getAddress())
                .build();
    }
}
