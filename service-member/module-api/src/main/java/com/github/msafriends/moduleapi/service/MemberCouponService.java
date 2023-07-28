package com.github.msafriends.moduleapi.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponCommand;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponUseRequest;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public ListResponse<MemberCouponResponse> getMemberCoupons(Long memberId, LocalDateTime currentTime) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not exist."));

        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberAndHasUsed(member, false);
        List<MemberCouponResponse> memberCouponResponses = memberCoupons.stream()
                .filter(memberCoupon -> memberCoupon.hasValidRangeCouponUse(currentTime))
                .map(MemberCouponResponse::from).toList();

        return new ListResponse<>(
                memberCouponResponses.size(),
                memberCouponResponses
        );
    }

    public ListResponse<MemberCouponResponse> useMemberCoupons(Long memberId, MemberCouponUseRequest request, LocalDateTime currentTime) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not exist."));
        List<MemberCoupon> memberCoupons = member.getMemberCoupons().stream()
                .filter(memberCoupon -> request.getMemberCouponIds().contains(memberCoupon.getId()))
                .filter(memberCoupon -> memberCoupon.hasValidRangeCouponUse(currentTime))
                .peek(memberCoupon -> memberCoupon.use(currentTime))
                .toList();

        validateRequestedCouponsExist(request, memberCoupons);

        memberCouponRepository.saveAll(memberCoupons);

        return new ListResponse<>(memberCoupons.size(), memberCoupons.stream().map(MemberCouponResponse::from).toList());
    }

    private void validateRequestedCouponsExist(MemberCouponUseRequest request, List<MemberCoupon> memberCoupons) {
        List<Long> missingCouponIds = request.getMemberCouponIds().stream()
                .filter(requestedCouponId -> memberCoupons.stream().noneMatch(memberCoupon -> memberCoupon.getId().equals(requestedCouponId))).toList();

        if (!missingCouponIds.isEmpty()) {
            throw new RuntimeException("사용할 수 있는 쿠폰 중에 쿠폰 ID " + missingCouponIds + "에 해당하는 쿠폰이 없습니다.");
        }
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
