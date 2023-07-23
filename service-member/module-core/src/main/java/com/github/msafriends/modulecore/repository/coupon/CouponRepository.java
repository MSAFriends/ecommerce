package com.github.msafriends.modulecore.repository.coupon;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
