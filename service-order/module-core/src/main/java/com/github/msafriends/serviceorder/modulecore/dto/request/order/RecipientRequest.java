package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipientRequest {
    @NotEmpty
    private final String name;

    @NotEmpty
    private final String phoneNumber;

    @NotEmpty
    private final String address;

    @Builder
    public RecipientRequest(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Recipient toRecipient(RecipientRequest recipientRequest) {
        return Recipient.builder()
                .name(recipientRequest.getName())
                .phoneNumber(recipientRequest.getPhoneNumber())
                .address(recipientRequest.getAddress())
                .build();
    }
}
