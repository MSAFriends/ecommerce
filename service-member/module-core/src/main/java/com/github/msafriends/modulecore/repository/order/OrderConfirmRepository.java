package com.github.msafriends.modulecore.repository.order;

import com.github.msafriends.modulecore.domain.order.OrderConfirm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderConfirmRepository extends JpaRepository<OrderConfirm, Long> {
}
