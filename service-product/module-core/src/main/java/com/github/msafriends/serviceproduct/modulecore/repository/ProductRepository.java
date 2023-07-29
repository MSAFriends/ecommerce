package com.github.msafriends.serviceproduct.modulecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
