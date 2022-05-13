package com.jibsakim.playgroundspringbootjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import com.jibsakim.playgroundspringbootjpa.service.ProductService;
import com.jibsakim.playgroundspringbootjpa.web.dto.ProductAddRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private ProductService productService;

    // todo: controller에 service넣고 생성자주입 해줘야함

    @Test
    void test() throws Exception {
        ProductAddRequestDto givenRequestDto = ProductAddRequestDto.builder()
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
                post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(givenRequestDto))
        ).andExpect(status().isOk());
    }
}
