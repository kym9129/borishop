package com.borishop.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, CartProductRepositoryCustom {
}
