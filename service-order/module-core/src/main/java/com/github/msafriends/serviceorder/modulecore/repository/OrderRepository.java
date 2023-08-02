package com.github.msafriends.serviceorder.modulecore.repository;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);

    Optional<Order> findByMemberIdAndStatus(Long memberId, OrderStatus status);

    List<Order> findAllByMemberIdAndStatus(Long memberId, OrderStatus status);
}
