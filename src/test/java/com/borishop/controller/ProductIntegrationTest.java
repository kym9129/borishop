package com.borishop.controller;

import com.borishop.domain.product.Product;
import com.borishop.domain.product.ProductRepository;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import com.borishop.constant.ProductSellStatus;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tear_down() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    void product_add() throws Exception {
        // given
        ProductCreateRequestDto givenRequestDto = ProductCreateRequestDto.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build();

        String url = "http://localhost:"+port+"/product";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, givenRequestDto, Long.class);

        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).isGreaterThan(0L)
        );

        List<Product> all = productRepository.findAll();
        assertAll(
                () -> assertThat(all.get(0).getProductName()).isEqualTo(givenRequestDto.getProductName()),
                () -> assertThat(all.get(0).getPrice()).isEqualTo(givenRequestDto.getPrice()),
                () -> assertThat(all.get(0).getStockNumber()).isEqualTo(givenRequestDto.getStockNumber()),
                () -> assertThat(all.get(0).getProductDetail()).isEqualTo(givenRequestDto.getProductDetail()),
                () -> assertThat(all.get(0).getProductSellStatus()).isEqualTo(givenRequestDto.getProductSellStatus())
        );

    }

    @Test
    @DisplayName("상품 수정 테스트")
    void update(){
        // given
        Product saveProduct = productRepository.save(Product.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build());

        Long updateId = saveProduct.getId();
        ProductUpdateRequestDto givenRequestDto = ProductUpdateRequestDto.builder()
                .productName("[100개 한정] 보리의 방울 장난감")
                .price(3000)
                .stockNumber(10)
                .productDetail("보리가 아주 좋아하는 딸랑딸랑 방울 장난감입니다.")
                .productSellStatus(ProductSellStatus.WAIT)
                .build();

        String url = "http://localhost:"+port+"/product/"+updateId;

        HttpEntity<ProductUpdateRequestDto> requestEntity = new HttpEntity<>(givenRequestDto);

        // when
        // PUT은 putForEntity가 없어서 exchange를 사용해서 보내나보다
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Product product = productRepository.findById(updateId).orElseThrow(null);
        assertAll(
                () -> assertThat(product.getProductName()).isEqualTo(givenRequestDto.getProductName()),
                () -> assertThat(product.getPrice()).isEqualTo(givenRequestDto.getPrice()),
                () -> assertThat(product.getStockNumber()).isEqualTo(givenRequestDto.getStockNumber()),
                () -> assertThat(product.getProductDetail()).isEqualTo(givenRequestDto.getProductDetail()),
                () -> assertThat(product.getProductSellStatus()).isEqualTo(givenRequestDto.getProductSellStatus())
        );

    }

}
