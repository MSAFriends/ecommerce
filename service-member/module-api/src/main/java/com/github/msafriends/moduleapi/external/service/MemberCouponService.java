package com.github.msafriends.moduleapi.external.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponCommand;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.repository.coupon.CouponRepository;
import com.github.msafriends.modulecore.repository.coupon.MemberCouponRepository;
import com.github.msafriends.modulecore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public MemberCouponResponse generateMemberCoupon(Long memberId, MemberCouponRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not exist."));
        Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow(() -> new RuntimeException("Coupon not exist."));

        validateHasMemberCouponAlready(member, coupon);

        MemberCoupon memberCoupon = new MemberCouponCommand(member, coupon, LocalDateTime.now()).toMemberCoupon();
        memberCouponRepository.save(memberCoupon);
        return MemberCouponResponse.from(memberCoupon);
    }

    private void validateHasMemberCouponAlready(Member member, Coupon coupon) {
        Optional<MemberCoupon> memberCoupon = memberCouponRepository.findMemberCouponsByMemberAndCoupon(member, coupon);
        if (memberCoupon.isPresent()) {
            throw new RuntimeException("memberCoupon already exist.");
        }
    }
}
