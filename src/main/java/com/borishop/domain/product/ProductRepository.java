package com.borishop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    List<Product> findByProductName(String productName);

    @Query("SELECT P FROM product AS P " +
            "WHERE P.productDetail LIKE %:productDetail% ")
    List<Product> findByProductDetail(@Param("productDetail")String productDetail);
}
