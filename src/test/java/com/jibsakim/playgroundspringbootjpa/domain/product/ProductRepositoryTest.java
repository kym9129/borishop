package com.jibsakim.playgroundspringbootjpa.domain.product;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void create_product_test(){
        // given
        Product givenProduct = Product.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        assertAll(
                () -> assertThat(savedProduct.getProductName()).isEqualTo(givenProduct.getProductName()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice()),
                () -> assertThat(savedProduct.getStockNumber()).isEqualTo(givenProduct.getStockNumber()),
                () -> assertThat(savedProduct.getProductDetail()).isEqualTo(givenProduct.getProductDetail()),
                () -> assertThat(savedProduct.getProductSellStatus()).isEqualTo(givenProduct.getProductSellStatus())
        );
    }
}
