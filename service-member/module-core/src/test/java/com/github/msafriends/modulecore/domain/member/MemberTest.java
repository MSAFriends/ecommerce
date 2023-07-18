package com.github.msafriends.modulecore.domain.member;

import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class MemberTest {
    private static final String MEMBER_ADDRESS = "서울시 서대문구 홍제동";

    @Nested
    @DisplayName("멤버의 주소 업데이트 테스트")
    class AddressUpdateTests {

        @Test
        @DisplayName("멤버의 주소가 성공적으로 업데이트 된다.")
        void testUpdateMemberCurrentAddressWithValidAddress() {
            Member member = MemberFixture.createMember();
            member.updateCurrentAddress(MEMBER_ADDRESS);
            assertThat(member.getCurrentAddress()).isEqualTo(MEMBER_ADDRESS);
        }

        @Test
        @DisplayName("멤버의 주소가 blank이기 때문에 업데이트가 실패한다.")
        void testUpdateMemberCurrentAddressWithInValidAddress() {
            Member member = MemberFixture.createMember();
            assertThatThrownBy(() -> member.updateCurrentAddress(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Address must not be null or empty.");
        }
    }

    @Nested
    @DisplayName("멤버의 전화번호 업데이트 테스트")
    class PhoneNumberUpdateTests {
        @Test
        @DisplayName("멤버의 전화번호가 성공적으로 업데이트 된다.")
        void testUpdateMemberPhoneNumberWithValidPhoneNumber() {
            Member member = MemberFixture.createMember();
            member.updatePhoneNumber("010-1234-5678");
            assertThat(member.getPhoneNumber()).isEqualTo("010-1234-5678");

            member.updatePhoneNumber("02-1234-5678");
            assertThat(member.getPhoneNumber()).isEqualTo("02-1234-5678");

            member.updatePhoneNumber("02-123-4567");
            assertThat(member.getPhoneNumber()).isEqualTo("02-123-4567");

            member.updatePhoneNumber("010-123-4567");
            assertThat(member.getPhoneNumber()).isEqualTo("010-123-4567");
        }

        @Test
        @DisplayName("멤버의 전화번호가 잘못된 포맷으로 들어올 시 업데이트가 실패한다.")
        void testUpdateMemberPhoneNumberWithInValidPhoneNumberFormat() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() -> member.updatePhoneNumber("0-1234-5678"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid phone number format.");

            assertThatThrownBy(() -> member.updatePhoneNumber("02-12-5678"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid phone number format.");

            assertThatThrownBy(() -> member.updatePhoneNumber("02-1234-56"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid phone number format.");
        }

        @Test
        @DisplayName("멤버의 전화번호가 Blank로 들어올 시 업데이트가 실패한다.")
        void testUpdateMemberPhoneNumberWithBlankPhoneNumber() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() -> member.updatePhoneNumber(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Phone number must not be null or empty.");
        }
    }

    @Nested
    @DisplayName("멤버의 이메일 업데이트 테스트")
    class EmailUpdateTests {

        @Test
        @DisplayName("멤버의 이메일이 성공적으로 업데이트가 된다.")
        void testUpdateMemberEmailWithValidEmail() {
            Member member = MemberFixture.createMember();
            member.updateEmail("admin@example.com");
            assertThat(member.getEmail().getValue()).isEqualTo("admin@example.com");
        }

        @Test
        @DisplayName("멤버의 이메일 포맷이 잘못되어 이메일 업데이트가 실패한다.")
        void testUpdateMemberEmailWithInValidEmail() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() -> member.updateEmail("adminexample"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid email format.");

            assertThatThrownBy(() -> member.updateEmail("admin@"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid email format.");

            assertThatThrownBy(() -> member.updateEmail("admin@example.a"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid email format.");

            assertThatThrownBy(() -> member.updateEmail("@example.a"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid email format.");

        }

        @Test
        @DisplayName("멤버의 이메일이 blank로 들어올 시에 이메일 업데이트가 실패한다.")
        void testUpdateMemberEmailWithBlankEmail() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() -> member.updateEmail(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Email must not be null or empty.");

        }
    }
}