package com.github.msafriends.moduleapi.controller.internal.member;

import com.github.msafriends.moduleapi.dto.request.member.MemberCouponUseRequest;
import com.github.msafriends.moduleapi.dto.response.ListResponse;
import com.github.msafriends.moduleapi.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.moduleapi.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal/v1/members")
public class MemberInternalControllerV1 {
    private final MemberCouponService memberCouponService;

    @GetMapping("/{memberId}/coupons")
    public ResponseEntity<ListResponse<MemberCouponResponse>> getCoupons(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberCouponService.getMemberCoupons(memberId, LocalDateTime.now()));
    }

    @PostMapping("/{memberId}/coupons")
    public ResponseEntity<ListResponse<MemberCouponResponse>> useCoupons(
            @PathVariable Long memberId,
            @RequestBody MemberCouponUseRequest request) {
        return ResponseEntity.ok(memberCouponService.useMemberCoupons(memberId, request, LocalDateTime.now()));
    }

}