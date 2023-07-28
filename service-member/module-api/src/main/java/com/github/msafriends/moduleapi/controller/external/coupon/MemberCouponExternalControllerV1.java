package com.github.msafriends.moduleapi.controller.external.coupon;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponRequest;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.moduleapi.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/coupon")
public class MemberCouponExternalControllerV1 {

    private final MemberCouponService memberCouponService;

    @PostMapping
    public ResponseEntity<MemberCouponResponse> issueMemberCoupon(
            @RequestHeader("Member-Id") Long memberId,
            @RequestBody MemberCouponRequest request) {
         return ResponseEntity.ok(memberCouponService.generateMemberCoupon(memberId, request));
    }
}
