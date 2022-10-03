package com.borishop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    List<Product> findByName(String name);

    @Query("SELECT P FROM product AS P " +
            "WHERE P.detail LIKE %:detail% ")
    List<Product> findByDetail(@Param("detail")String detail);

    List<Product> findAllByOrderByIdDesc(); // The first "By" in a query method indicates the start of a path expression.
}
