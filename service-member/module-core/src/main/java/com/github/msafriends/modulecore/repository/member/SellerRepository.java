package com.github.msafriends.modulecore.repository.member;

import com.github.msafriends.modulecore.domain.member.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByNickName(String nickName);
}
