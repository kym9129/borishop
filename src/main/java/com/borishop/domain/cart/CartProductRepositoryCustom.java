package com.borishop.domain.cart;

import com.borishop.web.dto.cart.CartProductResponseDto;

import java.util.List;

public interface CartProductRepositoryCustom {
    List<CartProductResponseDto> getCartProductListByUserId(Long userId);
}
