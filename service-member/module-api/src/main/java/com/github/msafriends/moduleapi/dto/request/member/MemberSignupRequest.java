package com.github.msafriends.moduleapi.dto.request.member;

import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.moduleapi.validator.anntations.ValidMemberName;
import com.github.msafriends.moduleapi.validator.anntations.ValidPassword;
import com.github.msafriends.moduleapi.validator.anntations.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequest {

    @NotBlank(message = "Email is a required field")
    @Email(message = "The format of the email is not valid")
    private String email;

    @ValidPassword
    private String password;

    @ValidPhoneNumber
    private String phoneNumber;
    @ValidMemberName
    private String name;

    public MemberSignupRequest(final String email, final String password, final String phoneNumber, final String name) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .build();
    }

    @Override
    public String toString() {
        return "MemberSignupRequest{" +
                "email='" + email + "'" +
                "password='" + password + "'" +
                "phoneNumber='" + phoneNumber + "'" +
                "name='" + name + "}";
    }
}
