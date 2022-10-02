package com.borishop.web.dto.product;

import com.borishop.constant.ProductSellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDto {
    private String productName;
    private int price;
    private int stockNumber;
    private String productDetail;
    private ProductSellStatus productSellStatus;
}
