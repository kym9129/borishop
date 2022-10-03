package com.borishop.domain.cart;

import com.borishop.web.dto.cart.CartProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.borishop.domain.cart.QCart.cart;
import static com.borishop.domain.cart.QCartProduct.cartProduct;
import static com.borishop.domain.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class CartProductRepositoryCustomImpl implements CartProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CartProductResponseDto> getCartProductListByUserId(Long userId){
        return queryFactory
                .select (Projections.fields(CartProductResponseDto.class
                        , cartProduct.id.as("cartProductId")
                        , product.id.as("productId")
                        , product.name.as("productName")
                        , product.price
                        , cartProduct.count
                        ))
                .from(cart)
                .join(cartProduct).on(cart.id.eq(cartProduct.cart.id))
                .join(product).on(cartProduct.product.id.eq(product.id))
                .where(cart.user.id.eq(userId))
                .fetch();
    }

}
