package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientResponse {
    private String name;
    private String phoneNumber;
    private String address;

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
