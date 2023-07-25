package com.github.msafriends.serviceorder.moduleapi.repository;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);
}
