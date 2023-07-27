package com.github.msafriends.serviceorder.moduleapi.repository;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);

    @Query("SELECT o FROM Order o WHERE o.memberId = :memberId AND o.status = 'PENDING'")
    Optional<Order> findPendingOrder(Long memberId);
}
