package com.github.msafriends.moduleapi.external.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberSignupRequest;
import com.github.msafriends.moduleapi.dto.response.member.MemberSignupResponse;
import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponGenerateType;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.repository.coupon.CouponRepository;
import com.github.msafriends.modulecore.repository.coupon.MemberCouponRepository;
import com.github.msafriends.modulecore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final int SIGN_UP_THANK_COUPON_EXPIRATION_RANGE = 30;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberSignupResponse createMember(MemberSignupRequest memberSignupRequest) {
        Member member = memberSignupRequest.toMember();
        memberRepository.save(member);
        generateSignUpThanksMemberCoupons(member);
        return MemberSignupResponse.from(member);
    }

    private void generateSignUpThanksMemberCoupons(Member member) {
        List<Coupon> signUpThanksCoupons = couponRepository.findCouponByGenerateType(CouponGenerateType.SIGNUP_THANKS);
        List<MemberCoupon> memberCoupons = signUpThanksCoupons.stream()
                .map(coupon -> createMemberCoupon(member, coupon))
                .collect(Collectors.toList());
        memberCouponRepository.saveAll(memberCoupons);
    }

    private MemberCoupon createMemberCoupon(Member member, Coupon coupon) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationDate = currentTime.plusDays(SIGN_UP_THANK_COUPON_EXPIRATION_RANGE);

        return MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .startAt(currentTime)
                .endAt(expirationDate)
                .build();
    }
}
