package com.borishop.controller;

import com.borishop.domain.product.Product;
import com.borishop.domain.product.ProductRepository;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import com.borishop.constant.ProductSellStatus;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ProductApiTest {

    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    JPAQueryFactory jpaQueryFactory;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void tear_down() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록")
    @WithMockUser(roles="ADMIN")
    void product_add() throws Exception {
        // given
        ProductCreateRequestDto givenRequestDto = ProductCreateRequestDto.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build();

        String url = "http://localhost:"+port+"/api/product";

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenRequestDto))
        ).andExpect(status().isOk());

        List<Product> all = productRepository.findAll();
        assertAll(
                () -> assertThat(all.get(0).getName()).isEqualTo(givenRequestDto.getProductName()),
                () -> assertThat(all.get(0).getPrice()).isEqualTo(givenRequestDto.getPrice()),
                () -> assertThat(all.get(0).getStockNumber()).isEqualTo(givenRequestDto.getStockNumber()),
                () -> assertThat(all.get(0).getDetail()).isEqualTo(givenRequestDto.getProductDetail()),
                () -> assertThat(all.get(0).getSellStatus()).isEqualTo(givenRequestDto.getProductSellStatus())
        );
    }

    @Test
    @DisplayName("상품 수정 테스트")
    @WithMockUser(roles="ADMIN")
    void update() throws Exception {
        // given
        Product saveProduct = productRepository.save(Product.builder()
                .name("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .detail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .sellStatus(ProductSellStatus.SELL)
                .build());

        Long updateId = saveProduct.getId();
        ProductUpdateRequestDto givenRequestDto = ProductUpdateRequestDto.builder()
                .productName("[100개 한정] 보리의 방울 장난감")
                .price(3000)
                .stockNumber(10)
                .productDetail("보리가 아주 좋아하는 딸랑딸랑 방울 장난감입니다.")
                .productSellStatus(ProductSellStatus.WAIT)
                .build();

        String url = "http://localhost:"+port+"/api/product/"+updateId;

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenRequestDto))
        ).andExpect(status().isOk());

        // then
        Product product = productRepository.findById(updateId).orElseThrow(null);
        assertAll(
                () -> assertThat(product.getName()).isEqualTo(givenRequestDto.getProductName()),
                () -> assertThat(product.getPrice()).isEqualTo(givenRequestDto.getPrice()),
                () -> assertThat(product.getStockNumber()).isEqualTo(givenRequestDto.getStockNumber()),
                () -> assertThat(product.getDetail()).isEqualTo(givenRequestDto.getProductDetail()),
                () -> assertThat(product.getSellStatus()).isEqualTo(givenRequestDto.getProductSellStatus())
        );
    }

}
