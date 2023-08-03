package com.github.msafriends.moduleapi.service;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponCommand;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.request.member.MemberCouponUseRequest;
import com.github.msafriends.moduleapi.dto.response.ListResponse;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponAlreadyIssuedException;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponExceedLimitedQuantityException;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponNotExistException;
import com.github.msafriends.modulecommon.exception.member.coupon.CouponOutOfStockException;
import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponGenerateType;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.repository.coupon.CouponRepository;
import com.github.msafriends.modulecore.repository.coupon.MemberCouponRepository;
import com.github.msafriends.modulecore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int DAILY_UNLIMITED_COUPON_ALLOW_QUANTITY = 1;
    private static final int COUPON_QUANTITY_THRESHOLD = 0;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
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

    @Transactional
    public MemberCouponResponse generateMemberCoupon(Long memberId, MemberCouponRequest request) {
        Member member = memberRepository.findByEmailOrElseThrow(memberId);
        Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow(() -> new RuntimeException("Coupon not exist."));

        if (coupon.getGenerateType().equals(CouponGenerateType.DAILY_LIMITED)) {
            validateDailyLimitedMemberCoupon(member, coupon);
            coupon.reduceQuantity(1);
            couponRepository.save(coupon);
        }

        if(coupon.getGenerateType().equals(CouponGenerateType.DAILY_UNLIMITED)) {
            validateDailyUnLimitedMemberCoupon(member, coupon);
        }

        MemberCoupon memberCoupon = new MemberCouponCommand(member, coupon, LocalDateTime.now()).toMemberCoupon();
        memberCouponRepository.save(memberCoupon);
        return MemberCouponResponse.from(memberCoupon);
    }

    private void validateDailyUnLimitedMemberCoupon(Member member, Coupon coupon) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findMemberCouponsByMemberAndCoupon(member, coupon);

        if (memberCoupons.size() == DAILY_UNLIMITED_COUPON_ALLOW_QUANTITY) {
            throw new CouponAlreadyIssuedException(memberCoupons.get(0).getId(), memberCoupons.get(0).getCoupon().getName());
        }
    }

    private void validateDailyLimitedMemberCoupon(Member member, Coupon coupon) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findMemberCouponsByMemberAndCoupon(member, coupon);

        if (memberCoupons.size() == coupon.getMaxQuantityPerMember()) {
            throw new CouponExceedLimitedQuantityException(coupon.getMaxQuantityPerMember());
        }

        if (coupon.getValue() <= COUPON_QUANTITY_THRESHOLD) {
            throw new CouponOutOfStockException();
        }
    }
}
