package com.jibsakim.playgroundspringbootjpa.web.dto.product;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductUpdateRequestDto {
    private String productName;
    private int price;
    private int stockNumber;
    private String productDetail;
    private ProductSellStatus productSellStatus;
}
