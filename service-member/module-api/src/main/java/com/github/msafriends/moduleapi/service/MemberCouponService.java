package com.github.msafriends.moduleapi.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponCommand;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponUseRequest;
import com.github.msafriends.moduleapi.dto.response.ListResponse;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponAlreadyIssuedException;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponNotExistException;
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

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public ListResponse<MemberCouponResponse> getMemberCoupons(Long memberId, LocalDateTime currentTime) {
        Member member = memberRepository.findByEmailOrElseThrow(memberId);

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
        Member member = memberRepository.findByEmailOrElseThrow(memberId);
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
            throw new CouponNotExistException(missingCouponIds);
        }
    }


    public MemberCouponResponse generateMemberCoupon(Long memberId, MemberCouponRequest request) {
        Member member = memberRepository.findByEmailOrElseThrow(memberId);
        Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow(() -> new RuntimeException("Coupon not exist."));

        validateHasMemberCouponAlready(member, coupon);

        MemberCoupon memberCoupon = new MemberCouponCommand(member, coupon, LocalDateTime.now()).toMemberCoupon();
        memberCouponRepository.save(memberCoupon);
        return MemberCouponResponse.from(memberCoupon);
    }

    private void validateHasMemberCouponAlready(Member member, Coupon coupon) {
        memberCouponRepository.findMemberCouponsByMemberAndCoupon(member, coupon).ifPresent(existingMemberCoupon -> {
                    throw new CouponAlreadyIssuedException(existingMemberCoupon.getId(), existingMemberCoupon.getCoupon().getName());
                }
        );
    }
}
