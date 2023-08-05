package com.github.msafriends.serviceproduct.modulecore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findProductsByIdIn(List<Long> ids);
}
