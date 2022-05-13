package com.jibsakim.playgroundspringbootjpa.web.dto;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import com.jibsakim.playgroundspringbootjpa.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@Builder
public class ProductAddRequestDto {
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String productName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockNumber;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String productDetail;

    private ProductSellStatus productSellStatus;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .price(price)
                .stockNumber(stockNumber)
                .productDetail(productDetail)
                .productSellStatus(productSellStatus)
                .build();
    }
}
