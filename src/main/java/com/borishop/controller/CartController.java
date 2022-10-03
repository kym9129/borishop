package com.borishop.controller;

import com.borishop.service.CartService;
import com.borishop.web.dto.cart.CartProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public ResponseEntity<?> getCartProductList(){
        List<CartProductResponseDto> cartProductList =  cartService.getCartProductList();
        return ResponseEntity.ok(cartProductList);
    }
}
