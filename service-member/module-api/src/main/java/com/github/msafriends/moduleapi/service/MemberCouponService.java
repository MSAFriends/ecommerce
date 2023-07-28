package com.github.msafriends.moduleapi.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponCommand;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.response.ListResponse;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public ListResponse<MemberCouponResponse> getMemberCoupons(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not exist."));

        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberAndHasUsed(member, false);
        return new ListResponse<>(memberCoupons.size(), memberCoupons.stream().map(MemberCouponResponse::from).toList());
    }

    public MemberCouponResponse generateMemberCoupon(Long memberId, MemberCouponRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not exist."));
        Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow(() -> new RuntimeException("Coupon not exist."));

        validateHasMemberCouponAlready(member, coupon);

        MemberCoupon memberCoupon = new MemberCouponCommand(member, coupon, LocalDateTime.now()).toMemberCoupon();
        memberCouponRepository.save(memberCoupon);
        return MemberCouponResponse.from(memberCoupon);
    }

    private void validateHasMemberCouponAlready(Member member, Coupon coupon) {
        memberCouponRepository.findMemberCouponsByMemberAndCoupon(member, coupon).ifPresent(existingMemberCoupon -> {
                    throw new RuntimeException("memberCoupon already exists.");
                }
        );
    }
}
