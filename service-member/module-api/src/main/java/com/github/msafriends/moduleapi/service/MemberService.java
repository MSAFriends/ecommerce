package com.github.msafriends.moduleapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.msafriends.moduleapi.dto.request.member.MemberSignupRequest;
import com.github.msafriends.moduleapi.dto.response.member.MemberSignupResponse;
import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponGenerateType;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.exception.member.member.MemberAlreadyExistException;
import com.github.msafriends.modulecore.repository.coupon.CouponRepository;
import com.github.msafriends.modulecore.repository.coupon.MemberCouponRepository;
import com.github.msafriends.modulecore.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberSignupResponse createMember(MemberSignupRequest memberSignupRequest) {
        Member member = memberSignupRequest.toMember();
        memberRepository.findByEmail(member.getEmail()).ifPresent(existingMember -> {
            throw new MemberAlreadyExistException(existingMember.getEmail().getValue());
        });

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
        LocalDateTime expirationDate = currentTime.plusDays(coupon.getValidationRange());

        return MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .startAt(currentTime)
                .endAt(expirationDate)
                .build();
    }
}
