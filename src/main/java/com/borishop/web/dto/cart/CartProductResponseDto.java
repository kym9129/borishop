package com.borishop.web.dto.cart;

import lombok.Getter;

@Getter
public class CartProductResponseDto {
    private Long cartProductId;
    private Long productId;
    private String productName;
    private int price;
    private int count;
}
