package com.borishop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.borishop.constant.ProductSellStatus;
import com.borishop.domain.product.Product;
import com.borishop.service.ProductService;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;

    private List<Product> productList;

    @BeforeEach
    void given_product_list(){
        productList = new ArrayList<>();
        for(long i = 1 ; i <= 10; i++){
            productList.add(Product.builder()
                    .id(i)
                    .name("[무료배송] 보리의 깃털장난감 "+i)
                    .price(5000)
                    .stockNumber(500)
                    .detail("보리가 아주 좋아하는 깃털 장난감입니다."+i)
                    .sellStatus(ProductSellStatus.SELL)
                    .build());
        }
    }

    @Test
    @DisplayName("상품 등록 controller 테스트")
    @Disabled("로그인 구현 중이라 임시로 패스")
    void create_product_test() throws Exception {
        ProductCreateRequestDto givenRequestDto = ProductCreateRequestDto.builder()
                .productName("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .productDetail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .productSellStatus(ProductSellStatus.SELL)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        given(productService.save(givenRequestDto))
                .willReturn(1L);

        mvc.perform(
                post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(givenRequestDto))
        ).andExpect(status().isOk());
    }
}
