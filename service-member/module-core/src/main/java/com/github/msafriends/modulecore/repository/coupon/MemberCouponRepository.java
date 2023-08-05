package com.github.msafriends.modulecore.repository.coupon;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.MemberCoupon;
import com.github.msafriends.modulecore.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    List<MemberCoupon> findAllByMemberAndHasUsed(Member member, boolean hasUsed);
    List<MemberCoupon> findMemberCouponsByMemberAndCoupon(Member member, Coupon coupon);
}
