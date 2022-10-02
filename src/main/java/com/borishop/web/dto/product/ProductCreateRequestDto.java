package com.borishop.web.dto.product;

import com.borishop.domain.product.Product;
import com.borishop.constant.ProductSellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequestDto {
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
                .name(productName)
                .price(price)
                .stockNumber(stockNumber)
                .detail(productDetail)
                .sellStatus(productSellStatus)
                .build();
    }
}
