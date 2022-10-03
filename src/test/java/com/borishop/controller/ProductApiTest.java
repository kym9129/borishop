package com.borishop.controller;

import com.borishop.domain.product.Product;
import com.borishop.domain.product.ProductRepository;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import com.borishop.constant.ProductSellStatus;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class ProductApiTest {

    private int port;
    private String host = "http://localhost:"+port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        // @WithMockUser는 MockMvc에서만 작동
        // @SpringBootTest에서 MockMvc를 사용하기 위한 설정
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
    @DisplayName("상품 등록 테스트")
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

        String url = host+"/api/product";

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

        String url = host+"/api/product/"+updateId;

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

    @Test
    @DisplayName("상품 1개 조회 테스트")
    @WithMockUser
    void get_product_one_test() throws Exception {

        // given
        ProductCreateRequestDto givenRequestDto = ProductCreateRequestDto.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build();
        Product saveProduct = productRepository.save(givenRequestDto.toEntity());

        // when
        mvc.perform(
                        get(host+"/api/product/" + saveProduct.getId())
                                .characterEncoding("UTF-8")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(saveProduct.getId().intValue())))
                .andExpect(jsonPath("$.productName", is(saveProduct.getName())))
                .andExpect(jsonPath("$.stockNumber", is(saveProduct.getStockNumber())));
    }

}
