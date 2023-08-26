package com.github.msafriends.serviceproduct.modulecore.repository;

import static com.github.msafriends.serviceproduct.modulecore.domain.product.QProduct.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.dto.product.DiscountOrder;
import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductSearchCondition;
import com.github.msafriends.serviceproduct.modulecore.dto.product.SatisfactionOrder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class ProductQueryRepository {
    private static final int PAGE_SIZE = 20;
    private final JPAQueryFactory queryFactory;
    public ProductQueryRepository(EntityManager entityManager){
        queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Product> readProductsWithConditions(ProductSearchCondition condition, long page){
        return queryFactory
            .selectFrom(product)
            .where(
                nameLike(condition.getKeyword()),
                priceGoe(condition.getMinPrice()),
                priceLoe(condition.getMaxPrice()),
                ageLimitEq(condition.getAgeLimit()),
                categoryEq(condition.getCategoryId())
            ).orderBy(createOrderSpecifier(
                condition.getSatisfactionOrder(),
                condition.getDiscountOrder())
            )
            .offset(page)
            .limit(PAGE_SIZE)
            .fetch();
    }

    private BooleanExpression nameLike(String keyword){
        return keyword != null ? product.name.containsIgnoreCase(keyword) : null;
    }
    private BooleanExpression priceGoe(Integer minPrice){
        return minPrice != null ? product.price.priceValue.goe(minPrice) : null;
    }
    private BooleanExpression priceLoe(Integer maxPrice){
        return maxPrice != null ? product.price.priceValue.loe(maxPrice) : null;
    }
    private BooleanExpression ageLimitEq(AgeLimit ageLimit){
        return ageLimit != null ? product.ageLimit.eq(ageLimit) : null;
    }
    private BooleanExpression categoryEq(Long categoryId){
        return categoryId != null ? product.category.id.eq(categoryId) : null;
    }

    private OrderSpecifier[] createOrderSpecifier(SatisfactionOrder satisfactionOrder, DiscountOrder discountOrder) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(satisfactionOrder != null){
            if(satisfactionOrder.equals(SatisfactionOrder.DESC)){
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.buySatisfy));
            }else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, product.buySatisfy));
            }
        }
        if(discountOrder != null){
            if(discountOrder.equals(DiscountOrder.DESC)){
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.benefit.discount));
            }else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, product.benefit.discount));
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
