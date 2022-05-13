package com.jibsakim.playgroundspringbootjpa.controller;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import com.jibsakim.playgroundspringbootjpa.domain.product.Product;
import com.jibsakim.playgroundspringbootjpa.domain.product.ProductRepository;
import com.jibsakim.playgroundspringbootjpa.web.dto.ProductAddRequestDto;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
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
    @DisplayName("상품 등록 테스트 by RestTemplate")
    void product_add() throws Exception {
        // given
        ProductAddRequestDto givenRequestDto = ProductAddRequestDto.builder()
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

}
