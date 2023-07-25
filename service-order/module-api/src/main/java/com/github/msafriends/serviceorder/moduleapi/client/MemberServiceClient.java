package com.github.msafriends.serviceorder.moduleapi.client;

import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.OrderCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/api/internal/v1/members/{memberId}/coupons")
    List<OrderCouponResponse> getAvailableCouponsByMemberId(@PathVariable Long memberId);
}
