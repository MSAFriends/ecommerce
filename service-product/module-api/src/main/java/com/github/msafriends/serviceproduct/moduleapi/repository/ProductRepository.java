package com.github.msafriends.serviceproduct.moduleapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
