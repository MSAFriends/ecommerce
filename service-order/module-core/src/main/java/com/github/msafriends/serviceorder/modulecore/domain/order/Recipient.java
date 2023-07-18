package com.github.msafriends.serviceorder.modulecore.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipient {

    @Column(name = "recipient_name", nullable = false)
    private String name;

    @Column(name = "recipient_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "recipient_address", nullable = false)
    private String address;

    @Builder
    public Recipient(String name, String phoneNumber, String address) {
        validateRecipient(name, phoneNumber, address);

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    private void validateRecipient(String name, String phoneNumber, String address) {
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(phoneNumber, "phoneNumber must not be empty");
        Assert.hasText(address, "address must not be empty");
    }
}
