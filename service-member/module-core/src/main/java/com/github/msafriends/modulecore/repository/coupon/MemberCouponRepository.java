package com.github.msafriends.modulecore.repository.coupon;

import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
