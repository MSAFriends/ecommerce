package com.github.msafriends.modulecore.repository.member;

import com.github.msafriends.modulecore.domain.member.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
