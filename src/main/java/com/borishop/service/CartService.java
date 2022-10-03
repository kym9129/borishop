package com.borishop.service;

import com.borishop.domain.cart.CartProductRepository;
import com.borishop.domain.user.User;
import com.borishop.domain.user.UserRepository;
import com.borishop.exception.NotFoundMemberException;
import com.borishop.util.SecurityUtil;
import com.borishop.web.dto.cart.CartProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final CartProductRepository cartProductRepository;

    public List<CartProductResponseDto> getCartProductList() {
        String currentEmail = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다"));

        List<CartProductResponseDto> cartProductList = cartProductRepository.getCartProductListByUserId(user.getId());
        // todo: 장바구니 비어있을 경우 "비어있습니다" 메세지 리턴

        return cartProductList;
    }
}
