package com.github.msafriends.serviceorder.moduleapi.client;

import com.github.msafriends.serviceorder.modulecore.dto.request.coupon.MemberCouponUseRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.ListResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.MemberCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @PostMapping("/api/internal/v1/members/{memberId}/coupons")
    ListResponse<MemberCouponResponse> useCoupons(@PathVariable Long memberId, @RequestBody MemberCouponUseRequest request);
}
