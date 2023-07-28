package com.github.msafriends.moduleapi.external.controller.coupon;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.moduleapi.external.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/coupon")
public class MemberCouponControllerV1 {

    private final MemberCouponService memberCouponService;

    @PostMapping
    public ResponseEntity<MemberCouponResponse> issueMemberCoupon(
            @RequestHeader("Member-Id") Long memberId,
            @RequestBody MemberCouponRequest request) {
         return ResponseEntity.ok(memberCouponService.generateMemberCoupon(memberId, request));
    }
}
