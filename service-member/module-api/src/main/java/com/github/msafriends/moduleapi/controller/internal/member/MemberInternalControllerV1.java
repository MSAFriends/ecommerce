package com.github.msafriends.moduleapi.controller.internal.member;

import com.github.msafriends.moduleapi.dto.response.ListResponse;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.moduleapi.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal/v1/members")
public class MemberInternalControllerV1 {
    private final MemberCouponService memberCouponService;

    @GetMapping("/{memberId}/coupons")
    public ResponseEntity<ListResponse<MemberCouponResponse>> getCoupons(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberCouponService.getMemberCoupons(memberId));
    }
}