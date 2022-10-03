package com.borishop.domain.cart;

import com.borishop.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="cart_product")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // CartProduct : Cart = N : 1 → 여러개의(Many) 상품이 하나의(One) 카트에
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne // CartProduct : Product = N : 1 → 여러개의(Many) 카트에 하나의(One) 상품이
    @JoinColumn(name="product_id")
    private Product product;

    private int count;
}
