package com.borishop.domain.product;

import com.borishop.constant.ProductSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

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

    void create_product_list(){
        List<Product> productList = new ArrayList<>();
        for(int i = 1 ; i <= 10; i++){
            productList.add(Product.builder()
                    .productName("[무료배송] 보리의 깃털장난감 "+i)
                    .price(5000)
                    .stockNumber(500)
                    .productDetail("보리가 아주 좋아하는 깃털 장난감입니다."+i)
                    .productSellStatus(ProductSellStatus.SELL)
                    .build());
        }
        productRepository.saveAll(productList);
    }

    @Test
    @DisplayName("상품명으로 상품 조회 테스트")
    void find_by_product_name_test(){
        create_product_list();
        String productName = "[무료배송] 보리의 깃털장난감 1";
        List<Product> productList = productRepository.findByProductName(productName);
        assertThat(productList).extracting("productName").containsOnly(productName);
    }

    @Test
    @DisplayName("JPQL로 상품 조회 테스트")
    void find_by_product_detail_using_jpql(){
        create_product_list();
        List<String> productDetailList = new ArrayList<>();
        String productDetail = "보리가 아주 좋아하는 깃털 장난감입니다.";
        for(int i = 1; i <= 10; i++){
            productDetailList.add(productDetail+i);
        }
        List<Product> productList = productRepository.findByProductDetail(productDetail);
        assertThat(productList).extracting("productDetail").containsAll(productDetailList);
    }
}
